package com.example.demo.model;

import com.example.demo.form.CustomerForm;

public class CustomerInfo {

	public CustomerInfo() {
		super();

	}

	public CustomerInfo(CustomerForm customerForm) {
		super();
		this.name = customerForm.getName();
		this.address = customerForm.getAddress();
		this.email = customerForm.getEmail();
		this.phone = customerForm.getPhone();
		this.valid = customerForm.isValid();
	}

	private String name;
	private String address;
	private String email;
	private String phone;
	private boolean valid;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public boolean isValid() {
		return valid;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
	}
}
