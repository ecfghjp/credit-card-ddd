package com.ecfghjp.credit.service.valueobjects;

public class AssignLimitVO {

	private double limitAssigned;
	private String creditCardNumber;
	private String assignedDate;

	public AssignLimitVO(double limitAssigned, String creditCardNumber, String assignedDate) {
		this.limitAssigned = limitAssigned;
		this.creditCardNumber = creditCardNumber;
		this.assignedDate = assignedDate;
	}

	public double getLimitAssigned() {
		return limitAssigned;
	}

	public String getCreditCardNumber() {
		return creditCardNumber;
	}

	public String getAssignedDate() {
		return assignedDate;
	}

}
