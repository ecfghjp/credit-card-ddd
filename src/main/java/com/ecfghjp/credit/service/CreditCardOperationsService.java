package com.ecfghjp.credit.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecfghjp.credit.controller.domain.CreditCardRequestDTO;
import com.ecfghjp.credit.controller.domain.PaymentRequestDTO;
import com.ecfghjp.credit.exception.CreditLimitAlreadyRegistered;
import com.ecfghjp.credit.service.domain.CreditCard;
import com.ecfghjp.credit.service.domain.CreditCardConstants;
import com.ecfghjp.credit.service.domain.CreditCardNumber;
import com.ecfghjp.credit.service.domain.CreditCardOperationAggregate;
import com.ecfghjp.credit.service.domain.CreditCardTransaction;
import com.ecfghjp.credit.service.domain.CreditLimit;
import com.ecfghjp.credit.service.domain.TransactionId;
import com.ecfghjp.credit.service.repository.CreditCardRepository;
import com.ecfghjp.credit.service.repository.CreditCardTransactionsRepository;

@Service
public class CreditCardOperationsService {

	private CreditCardTransactionsRepository creditCardOperationsRepository;
	private CreditCardRepository creditCardRepository;

	public CreditCardOperationsService() {
	}

	@Autowired
	public CreditCardOperationsService(CreditCardTransactionsRepository creditCardOperationsRepository,
			CreditCardRepository creditCardRepository) {
		this.creditCardOperationsRepository = creditCardOperationsRepository;
		this.creditCardRepository = creditCardRepository;
	}

	public CreditCardTransaction payment(PaymentRequestDTO paymentRequestDTO) {
		CreditCardTransaction previousTransaction = creditCardOperationsRepository
				.findLastCreditCardTransaction(paymentRequestDTO.getCreditCardNumber());
		
		// get CreditCard details for the Credit number
		CreditCard creditCardDetails = creditCardRepository
				.findCreditCardDetails(paymentRequestDTO.getCreditCardNumber());
		
		//create aggregate root should be via builder and factory
		CreditCardOperationAggregate creditCardOperationAggregate = new CreditCardOperationAggregate.Builder(
				creditCardDetails).withPreviousTransaction(previousTransaction).build();

		CreditCardTransaction transaction = creditCardOperationAggregate
				.transaction(paymentRequestDTO.getTransactionAmount(), paymentRequestDTO.getTransactionPurpose());
		// save the current credit card transaction
		TransactionId transactionId = creditCardOperationsRepository.persistTransaction(paymentRequestDTO.getCreditCardNumber(),transaction);
		transaction.addTransactionId(transactionId);
		return transaction;
	}

	public String assignLimit(CreditCardRequestDTO creditCardDTO) {
		// TODO Auto-generated method stub
		CreditCard creditCard = new CreditCard(new CreditCardNumber(creditCardDTO.getCreditCardNumber()), new CreditLimit(creditCardDTO.getLimitAssigned()));
		//search for credit card first , if present dont assign
		CreditCard existingCreditCard =  creditCardRepository.findCreditCardDetails(creditCardDTO.getCreditCardNumber());
		if(null==existingCreditCard) {
			creditCardRepository.saveCreditCard(creditCard);
			return CreditCardConstants.LIMIT_ASSIGNED;
			

		}else {
			throw new CreditLimitAlreadyRegistered();
		}
	}


}
