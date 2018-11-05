package com.example.demo.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "Orders", uniqueConstraints = { @UniqueConstraint(columnNames = "Order_Num") })
public class Order  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1959442579758951751L;

	@Id
	@Column(name = "ID", length = 30, nullable = false)
	private String id;

	@Column(name = "Amount", length = 30, nullable = false)
	private double amount;

	@Column(name = "Customer_Address", length = 30, nullable = false)
	private String customerAddress;

	@Column(name = "Customer_Email", length = 30, nullable = false)
	private String customerEmail;

	@Column(name = "Customer_Name", length = 30, nullable = false)
	private String customerName;

	@Column(name = "Customer_Phone", length = 30, nullable = false)
	private String customerPhone;

	@Temporal(TemporalType.DATE)
	@Column(name = "Order_Date", nullable = false)
	private Date orderDate;

	@Column(name = "Order_Num", nullable = false)
	private int orderNum;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}



	public String getCustomerAddress() {
		return customerAddress;
	}

	public void setCustomerAddress(String customerAddress) {
		this.customerAddress = customerAddress;
	}

	public String getCustomerEmail() {
		return customerEmail;
	}

	public void setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCustomerPhone() {
		return customerPhone;
	}

	public void setCustomerPhone(String customerPhone) {
		this.customerPhone = customerPhone;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public int getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(int orderNum) {
		this.orderNum = orderNum;
	}

	@Override
	public String toString() {
		return "Order [id=" + id + ", amount=" + amount + ", customerAddress=" + customerAddress + ", customerEmail="
				+ customerEmail + ", customerName=" + customerName + ", customerPhone=" + customerPhone + ", orderDate="
				+ orderDate + ", orderNum=" + orderNum + "]";
	}

	

}
