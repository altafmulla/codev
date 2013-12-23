package com.hvk.societmaintain.model;

import java.io.Serializable;

public class UserSummary implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String firstName;
	private String userId;
	private String lastName;
	private String userType;
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	
}
