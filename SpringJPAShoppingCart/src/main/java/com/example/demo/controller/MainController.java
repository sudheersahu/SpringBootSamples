package com.example.demo.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.dao.AccountDAO;
import com.example.demo.dao.OrderDAO;
import com.example.demo.dao.ProductDAO;
import com.example.demo.entity.Product;
import com.example.demo.form.CustomerForm;
import com.example.demo.model.CartInfo;
import com.example.demo.model.CustomerInfo;
import com.example.demo.model.ProductInfo;
import com.example.demo.pagination.PaginationResult;
import com.example.demo.utils.Utils;
import com.example.demo.validator.CustomerFormValidator;

@Controller
public class MainController {

	@Autowired
	AccountDAO accountDao;

	@Autowired
	ProductDAO productDao;

	@Autowired
	OrderDAO orderDao;

	@Autowired
	CustomerFormValidator customerFormValidator;

	@InitBinder
	public void myInitBinder(WebDataBinder binder) {
		Object target = binder.getTarget();

		if (target == null)
			return;

		System.out.println("Target=" + target);

		if (target.getClass() == CartInfo.class) {

		} else if (target.getClass() == CustomerForm.class) {
			binder.setValidator(customerFormValidator);
		}
	}

	@RequestMapping("/403")
	public String accessDenied() {

		return "403";
	}

	@RequestMapping("/")
	public String home() {
		return "index";
	}

	@RequestMapping({ "/productList" })
	public String listProductHandler(Model model, @RequestParam(value = "name", defaultValue = "") String likeName,
			@RequestParam(value = "page", defaultValue = "1") int page) {

		final int maxResult = 5;
		final int maxNavigationPage = 10;
		PaginationResult<ProductInfo> result = productDao.queryProducts(page, maxResult, maxNavigationPage, likeName);
		model.addAttribute("pagiantionProducts", result);
		return "productList";
	}

	@RequestMapping({ "/buyProduct" })
	public String addProductHandler(HttpServletRequest request, Model model,
			@RequestParam(value = "code", defaultValue = "") String code) {

		Product product = null;
		if (code != null && code.length() > 0) {
			product = productDao.findProduct(code);
		}

		if (product != null) {
			CartInfo cartInfo = Utils.getCartInSession(request);
			ProductInfo productInfo = new ProductInfo(product);
			cartInfo.addProduct(productInfo, 1);
		}

		return "redirect:/shoppingCart";

	}

	@RequestMapping({ "/shoppingCartRemoveProduct" })
	public String removeProductHandler(HttpServletRequest request, Model model,
			@RequestParam(value = "code", defaultValue = "") String code) {

		Product product = null;

		if (code != null && code.length() > 0) {
			product = productDao.findProduct(code);
		}

		if (product != null) {
			CartInfo cartInfo = Utils.getCartInSession(request);
			ProductInfo productInfo = new ProductInfo(product);
			cartInfo.removeProduct(productInfo);
		}
		return "redirect:/shoppingCart";

	}

	@PostMapping(value = { "/shoppingCart" })
	public String shoppingCartUpdateQuantity(HttpServletRequest request, Model model,
			@ModelAttribute("cartForm") CartInfo cartForm) {

		CartInfo cartInfo = Utils.getCartInSession(request);
		cartInfo.updateQuantity(cartForm);
		return "redirect:/shoppingCart";

	}

	// show cart
	@GetMapping(value = { "/shoppingCart" })
	public String shoppingCartHandler(HttpServletRequest request, Model model) {

		CartInfo cartInfo = Utils.getCartInSession(request);
		model.addAttribute("cartForm", cartInfo);
		return "redirect:/shoppingCart";

	}

	// Enter customer Information
	@GetMapping(value = { "/shoppingCartCustomer" })
	public String shoppingCartCustomerForm(HttpServletRequest request, Model model) {
		CartInfo cartInfo = Utils.getCartInSession(request);
		if (cartInfo.isEmpty()) {

			return "redirect:/shoppingCart";
		}
		CustomerInfo customerInfo = cartInfo.getCustomerInfo();

		CustomerForm customerForm = new CustomerForm(customerInfo);
		model.addAttribute("customerForm", customerForm);
		return "shoppingCartCustomer";

	}

	@PostMapping(value = { "/shoppingCartCustomer" })
	public String shoppingCartCustomerSave(HttpServletRequest request, Model model,
			@ModelAttribute("customerForm") @Validated CustomerForm customerForm, BindingResult result) {

		CartInfo cartInfo = Utils.getCartInSession(request);

		if (result.hasErrors()) {
			customerForm.setValid(false);
			return "shoppingCartCustomer";
		}

		customerForm.setValid(true);

		CustomerInfo customerInfo = new CustomerInfo(customerForm);
		cartInfo.setCustomerInfo(customerInfo);
		return "redirect:/shoppingCartConfirmation";

	}

	@GetMapping(value = "{/shoppingCartConfirmation}")
	public String shoppingCartConfirmationReview(HttpServletRequest request, Model model) {
		CartInfo cartInfo = Utils.getCartInSession(request);

		if (cartInfo == null || cartInfo.isEmpty()) {
			return "redirect:/shoppingCart";
		} else if (!cartInfo.isValidCustomer()) {
			return "redirect:/shoppingCartCusotmer";
		}

		model.addAttribute("myCart", cartInfo);
		return "shoppingCartConfirmation";

	}

	@PostMapping(value = "{/shoppingCartConfirmation}")
	public String shoppingCartConfirmationSave(HttpServletRequest request, Model model) {
		CartInfo cartInfo = Utils.getCartInSession(request);

		if (cartInfo.isEmpty()) {
			return "redirect:/shoppingCart";
		} else if (!cartInfo.isValidCustomer()) {
			return "redirect:/shoppimgCartCustomer";
		}

		try {
			orderDao.saveOrder(cartInfo);
		} catch (Exception e) {
			return "shoppingCartConfirmation";
		}

		Utils.removeCartInSession(request);
		Utils.storeLastOrderedCartInSession(request, cartInfo);

		return "redirect:/shoppingCartFinalize";
	}

	@GetMapping(value = { "/shoppingCartFinalize" })
	public String shoppingCartFinalize(HttpServletRequest request, Model model) {
		CartInfo cartInfo = Utils.getLastOrderedCartInSession(request);

		if (cartInfo == null) {
			return "redirect:/shoppingCart";
		}
		model.addAttribute("lastOrderedCart", cartInfo);
		return "shoppingCartFinalize";
	}

	@GetMapping(value = { "/productImage" })
	public void productImage(HttpServletRequest request, HttpServletResponse response, Model model,
			@RequestParam("code") String code) throws IOException {
		Product product = null;
		if (code != null) {
			product = this.productDao.findProduct(code);
		}
		if (product != null && product.getImage() != null) {
			response.setContentType("image/jpeg, image/jpg, image/png, image/gif");
			response.getOutputStream().write(product.getImage());
		}
		response.getOutputStream().close();
	}
}
