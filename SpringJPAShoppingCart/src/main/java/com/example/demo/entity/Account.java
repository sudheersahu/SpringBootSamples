package com.example.demo.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Accounts")
public class Account implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9218558611057882209L;
	
	 public static final String ROLE_MANAGER = "MANAGER";
	 public static final String ROLE_EMPLOYEE = "EMPLOYEE";

	@Id
	@Column(name = "User_Name" , length = 30, nullable = false)
	private String userName;
	
	@Column(name = "Encryted_Password" , length = 128, nullable = false)
	private String encryptedPassword;
	
	@Column(name = "Active" ,length =1, nullable = false)
	private boolean active;
	
	@Column(name = "User_Role" , length = 30, nullable = false)
	private String userRole;
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getEncryptedPassword() {
		return encryptedPassword;
	}
	public void setEncryptedPassword(String encryptedPassword) {
		this.encryptedPassword = encryptedPassword;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	public String getUserRole() {
		return userRole;
	}
	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}
	@Override
	public String toString() {
		return "Account [userName=" + userName + ", encryptedPassword=" + encryptedPassword + ", active=" + active
				+ ", userRole=" + userRole + "]";
	}

	
}
