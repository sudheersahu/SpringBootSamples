package com.example.demo.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.example.demo.dao.ProductDAO;
import com.example.demo.entity.Product;
import com.example.demo.form.ProductForm;

@Component
public class ProductFormValidator implements Validator {

	@Autowired
	private ProductDAO productDAO;

	@Override
	public boolean supports(Class<?> clazz) {

		return clazz == ProductForm.class;
	}

	@Override
	public void validate(Object target, Errors errors) {

		ProductForm productInfo = (ProductForm) target;

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "NotEmpty.productForm.name");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "price", "NotEmpty.productForm.price");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "code", "NotEmpty.productForm.code");

		String code = productInfo.getCode();
		if (code != null && code.length() > 0) {
			if (code.matches("\\s+")) {
				errors.rejectValue("code", "Pattern.productForm.code");
			} else if (productInfo.isNewProduct()) {
				Product product = productDAO.findProduct(code);
				if (product != null) {
					errors.rejectValue("code", "Duplicate.productForm.code");
				}
			}
		}
	}

}
