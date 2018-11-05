package com.example.demo.model;

import java.util.ArrayList;
import java.util.List;

public class CartInfo {

	public CartInfo() {
		super();

	}

	private CustomerInfo customerInfo;
	private int orderNum;

	private List<CartLineInfo> cartlines = new ArrayList<CartLineInfo>();

	public CustomerInfo getCustomerInfo() {
		return customerInfo;
	}

	public void setCustomerInfo(CustomerInfo customerInfo) {
		this.customerInfo = customerInfo;
	}

	public int getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(int orderNum) {
		this.orderNum = orderNum;
	}

	public List<CartLineInfo> getCartlines() {
		return cartlines;
	}

	public void setCartlines(List<CartLineInfo> cartlines) {
		this.cartlines = cartlines;
	}

	private CartLineInfo findLineByCode(String code) {
		for (CartLineInfo line : this.cartlines) {
			if (line.getProductInfo().getCode().equals(code)) {
				return line;
			}
		}
		return null;

	}

	public void addProduct(ProductInfo productInfo, int quantity) {
		CartLineInfo line = this.findLineByCode(productInfo.getCode());

		if (line == null) {
			line = new CartLineInfo();
			line.setQuantity(0);
			line.setProductInfo(productInfo);
			this.cartlines.add(line);
		}

		int newQuantity = line.getQuantity() + quantity;
		if (quantity <= 0) {
			this.cartlines.remove(line);
		} else {
			line.setQuantity(newQuantity);
		}
	}

	public void updateProduct(String code, int quantity) {

		CartLineInfo line = this.findLineByCode(code);

		if (line != null) {
			if (quantity <= 0) {
				this.cartlines.remove(line);
			} else {
				line.setQuantity(quantity);
			}
		}
	}

	public void removeProduct(ProductInfo productInfo) {
		CartLineInfo line = this.findLineByCode(productInfo.getCode());
		if (line != null) {
			this.cartlines.remove(line);
		}

	}

	public boolean isEmpty() {
		return this.cartlines.isEmpty();
	}

	public int getQuantityTotal() {
		int quantity = 0;
		for (CartLineInfo line : this.cartlines) {
			quantity += line.getQuantity();
		}
		return quantity;
	}

	public double getAmountTotal() {
		double total = 0;
		for (CartLineInfo line : this.cartlines) {
			total += line.getAmount();
		}
		return total;
	}
	    
	public boolean isValidCustomer() {
		return this.customerInfo != null && this.customerInfo.isValid();
	}

	public void updateQuantity(CartInfo cartForm) {
		if (cartForm != null) {
			List<CartLineInfo> lines = cartForm.getCartlines();
			for (CartLineInfo line : lines) {
				this.updateProduct(line.getProductInfo().getCode(), line.getQuantity());
			}
		}

	}

}
