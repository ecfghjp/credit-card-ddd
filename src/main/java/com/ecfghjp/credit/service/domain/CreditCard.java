package com.ecfghjp.credit.service.domain;

//should just be persisted once 
//we should just be checking is the credit card number exist or not
public class CreditCard {
	
	private CreditCardNumber creditCardNumber;
	private CreditLimit creditLimit;
	
	
	public CreditCard(CreditCardNumber creditCardNumber, CreditLimit creditLimit) {
		this.creditCardNumber = creditCardNumber;
		this.creditLimit = creditLimit;
	}


	public CreditCardNumber getCreditCardNumber() {
		return creditCardNumber;
	}


	public CreditLimit getCreditLimit() {
		return creditLimit;
	}
	
	
	
	
	

}
