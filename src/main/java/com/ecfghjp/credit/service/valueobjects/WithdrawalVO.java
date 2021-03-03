package com.ecfghjp.credit.service.valueobjects;

public class WithdrawalVO {

	double withdrawalAmount;
	double remainingCreditAmount;
	String creditCardNumber;
	String withdrawalTime;

	public WithdrawalVO(double withdrawalAmount, double remainingCreditAmount, String creditCardNumber,
			String withdrawalTime) {
		this.withdrawalAmount = withdrawalAmount;
		this.remainingCreditAmount = remainingCreditAmount;
		this.creditCardNumber = creditCardNumber;
		this.withdrawalTime = withdrawalTime;
	}

	public double getWithdrawalAmount() {
		return withdrawalAmount;
	}

	public double getRemainingCreditAmount() {
		return remainingCreditAmount;
	}

	public String getCreditCardNumber() {
		return creditCardNumber;
	}

	public String getWithdrawalTime() {
		return withdrawalTime;
	}
}
