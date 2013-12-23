package com.hvk.societmaintain.model;

import java.io.Serializable;
import java.util.Date;

public class BillDetail implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String billId;
	private String apartmentId;
	private String buildingName;
	private String complexDescription;
	private String firstName;
	private String lastName;
	private String apartmentTypeDescription;
	private String utilityTypeDescription;
	private double originalAmountDue;
	private double amountOutstanding;
	private Date paymentDueDate;
	private Date issueDate;
	private Date paidDate;
	private String formattedPaymentDueDate;
	private String formattedIssueDate;
	private Date formattedPaidDate;
	private String chequeNumber;
	private double paidAmount;
	private String accountId;
	private String transactionType;
	
	public String getTransactionType() {
		return transactionType;
	}
	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}
	public String getAccountId() {
		return accountId;
	}
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
	public double getPaidAmount() {
		return paidAmount;
	}
	public void setPaidAmount(double paidAmount) {
		this.paidAmount = paidAmount;
	}
	public Date getPaidDate() {
		return paidDate;
	}
	public void setPaidDate(Date paidDate) {
		this.paidDate = paidDate;
	}
	public Date getFormattedPaidDate() {
		return formattedPaidDate;
	}
	public void setFormattedPaidDate(Date formattedPaidDate) {
		this.formattedPaidDate = formattedPaidDate;
	}
	public String getChequeNumber() {
		return chequeNumber;
	}
	public void setChequeNumber(String chequeNumber) {
		this.chequeNumber = chequeNumber;
	}
	public String getFormattedPaymentDueDate() {
		return formattedPaymentDueDate;
	}
	public void setFormattedPaymentDueDate(String formattedPaymentDueDate) {
		this.formattedPaymentDueDate = formattedPaymentDueDate;
	}
	public String getFormattedIssueDate() {
		return formattedIssueDate;
	}
	public void setFormattedIssueDate(String formattedIssueDate) {
		this.formattedIssueDate = formattedIssueDate;
	}
	public String getBillId() {
		return billId;
	}
	public void setBillId(String billId) {
		this.billId = billId;
	}
	public String getApartmentId() {
		return apartmentId;
	}
	public void setApartmentId(String apartmentId) {
		this.apartmentId = apartmentId;
	}
	public String getBuildingName() {
		return buildingName;
	}
	public void setBuildingName(String buildingName) {
		this.buildingName = buildingName;
	}
	public String getComplexDescription() {
		return complexDescription;
	}
	public void setComplexDescription(String complexDescription) {
		this.complexDescription = complexDescription;
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
	public String getApartmentTypeDescription() {
		return apartmentTypeDescription;
	}
	public void setApartmentTypeDescription(String apartmentTypeDescription) {
		this.apartmentTypeDescription = apartmentTypeDescription;
	}
	public String getUtilityTypeDescription() {
		return utilityTypeDescription;
	}
	public void setUtilityTypeDescription(String utilityTypeDescription) {
		this.utilityTypeDescription = utilityTypeDescription;
	}
	public double getOriginalAmountDue() {
		return originalAmountDue;
	}
	public void setOriginalAmountDue(double originalAmountDue) {
		this.originalAmountDue = originalAmountDue;
	}
	public double getAmountOutstanding() {
		return amountOutstanding;
	}
	public void setAmountOutstanding(double amountOutstanding) {
		this.amountOutstanding = amountOutstanding;
	}
	public Date getPaymentDueDate() {
		return paymentDueDate;
	}
	public void setPaymentDueDate(Date paymentDueDate) {
		this.paymentDueDate = paymentDueDate;
	}
	public Date getIssueDate() {
		return issueDate;
	}
	public void setIssueDate(Date issueDate) {
		this.issueDate = issueDate;
	}
}
