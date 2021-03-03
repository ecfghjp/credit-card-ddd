package com.ecfghjp.credit.service.repository;

import org.springframework.stereotype.Repository;

import com.ecfghjp.credit.service.domain.CreditCard;

@Repository
public interface CreditCardRepository {
	
	public CreditCard findCreditCardDetails(String creditCardNumber);
	public String saveCreditCard(CreditCard creditCard);


}
