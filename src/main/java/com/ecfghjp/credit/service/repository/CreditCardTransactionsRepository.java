package com.ecfghjp.credit.service.repository;

import org.springframework.stereotype.Repository;

import com.ecfghjp.credit.service.domain.CreditCardTransaction;
import com.ecfghjp.credit.service.domain.TransactionId;

@Repository
public interface CreditCardTransactionsRepository {

	public CreditCardTransaction findLastCreditCardTransaction(String creditCardNumber);

	public TransactionId persistTransaction(String creditCardNumber , CreditCardTransaction currentCreditCardTransaction);

}
