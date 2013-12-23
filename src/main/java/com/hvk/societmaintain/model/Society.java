package com.hvk.societmaintain.model;

import java.io.Serializable;

public class Society implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String name;
	
	private String fullName;
	
	private String address;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	
	

}
