package com.ecfghjp.credit.infrastructure.persistence.jpa;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ecfghjp.credit.infrastructure.persistence.jpa.entities.CreditCardTransactionJPA;
import com.ecfghjp.credit.service.domain.CreditCardTransaction;
import com.ecfghjp.credit.service.domain.TransactionId;
import com.ecfghjp.credit.service.domain.TransactionPurpose;
import com.ecfghjp.credit.service.repository.CreditCardTransactionsRepository;

@Component
public class CreditCardTransactionJPARepository implements CreditCardTransactionsRepository {

	@Autowired
	private CreditCardTransactionJPARepositotyInterface creditCardTransactionJPARepositotyInterface;

	@Override
	public CreditCardTransaction findLastCreditCardTransaction(String creditCardNumber) {
		// TODO Auto-generated method stub
		Optional<CreditCardTransactionJPA> fetchedValue = creditCardTransactionJPARepositotyInterface
				.findTopByCreditCardNumberOrderByTransactionDateDesc(creditCardNumber);
		if(fetchedValue.isEmpty()) {
			return null;
		}
		CreditCardTransactionJPA creditCardTransactionJPA = fetchedValue.get();
		CreditCardTransaction creditCardTransaction = new CreditCardTransaction(
				creditCardTransactionJPA.getTransactionAmount(),
				TransactionPurpose.valueOf(creditCardTransactionJPA.getTransactionPurpose()),
				creditCardTransactionJPA.getTransactionDate(), creditCardTransactionJPA.getCreditBeforeTransaction(),
				creditCardTransactionJPA.getCreditAfterTransaction());
		creditCardTransaction.addTransactionId(new TransactionId(creditCardTransactionJPA.getTransactionId()));

		return creditCardTransaction;
	}

	@Override
	public TransactionId persistTransaction(String creditCardNumber,
			CreditCardTransaction currentCreditCardTransaction) {
		// TODO Auto-generated method stub
		CreditCardTransactionJPA transaction = new CreditCardTransactionJPA(creditCardNumber,
				currentCreditCardTransaction.getTransactionAmount(), currentCreditCardTransaction.getTransactionDate(),
				currentCreditCardTransaction.getTransactionPurpose().toString(),
				currentCreditCardTransaction.creditForUseBeforeTransaction(),
				currentCreditCardTransaction.creditLeftAfterTransaction());
		CreditCardTransactionJPA persistedEntity = creditCardTransactionJPARepositotyInterface.save(transaction);
		return new TransactionId(persistedEntity.getTransactionId());
	}

}
