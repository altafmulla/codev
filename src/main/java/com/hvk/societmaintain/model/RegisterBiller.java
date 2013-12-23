package com.hvk.societmaintain.model;

import java.io.Serializable;
import java.util.Date;

public class RegisterBiller implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String complex;
	private String building;
	private String apartment;
	private String utilitytype;
	private String emailAlert;
	private String smsAlert;
	private String preferredTimeStart;
	public String getPreferredTimeStart() {
		return preferredTimeStart;
	}
	public void setPreferredTimeStart(String preferredTimeStart) {
		this.preferredTimeStart = preferredTimeStart;
	}
	public String getPreferredTimeEnd() {
		return preferredTimeEnd;
	}
	public void setPreferredTimeEnd(String preferredTimeEnd) {
		this.preferredTimeEnd = preferredTimeEnd;
	}
	private String preferredTimeEnd;
	public String getComplex() {
		return complex;
	}
	public void setComplex(String complex) {
		this.complex = complex;
	}
	public String getBuilding() {
		return building;
	}
	public void setBuilding(String building) {
		this.building = building;
	}
	public String getApartment() {
		return apartment;
	}
	public void setApartment(String apartment) {
		this.apartment = apartment;
	}
	public String getUtilitytype() {
		return utilitytype;
	}
	public void setUtilitytype(String utilitytype) {
		this.utilitytype = utilitytype;
	}
	public String getEmailAlert() {
		return emailAlert;
	}
	public void setEmailAlert(String emailAlert) {
		this.emailAlert = emailAlert;
	}
	public String getSmsAlert() {
		return smsAlert;
	}
	public void setSmsAlert(String smsAlert) {
		this.smsAlert = smsAlert;
	}
	
}
