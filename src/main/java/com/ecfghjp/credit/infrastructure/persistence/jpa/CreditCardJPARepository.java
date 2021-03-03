package com.ecfghjp.credit.infrastructure.persistence.jpa;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ecfghjp.credit.infrastructure.persistence.jpa.entities.CreditCardJPA;
import com.ecfghjp.credit.service.domain.CreditCard;
import com.ecfghjp.credit.service.domain.CreditCardNumber;
import com.ecfghjp.credit.service.domain.CreditLimit;
import com.ecfghjp.credit.service.repository.CreditCardRepository;

@Repository
public class CreditCardJPARepository implements CreditCardRepository {

	private CreditCardJPARepositoryInterface creditCardJPARepositoryInterface;

	public CreditCardJPARepository() {
	}

	@Autowired
	public CreditCardJPARepository(CreditCardJPARepositoryInterface creditCardJPARepositoryInterface) {
		this.creditCardJPARepositoryInterface = creditCardJPARepositoryInterface;
	}

	@Override
	public CreditCard findCreditCardDetails(String creditCardNumber) {
		// TODO Auto-generated method stub
		Optional<CreditCardJPA> creditCardJPA = creditCardJPARepositoryInterface
				.findByCreditCardNumber(creditCardNumber);
		// move it to converter
		if(creditCardJPA.isEmpty()) {
			return null;
		}
		CreditCard creditCard = new CreditCard(new CreditCardNumber(creditCardJPA.get().getCreditCardNumber()),
				new CreditLimit(creditCardJPA.get().getMaxLimit()));
		return creditCard;
	}

	@Override
	public String saveCreditCard(CreditCard creditCard) {
		// TODO Auto-generated method stub
		CreditCardJPA creditCardJPA = new CreditCardJPA(creditCard.getCreditCardNumber().getCreditCardNumber(),
				creditCard.getCreditLimit().getLimit());
		return creditCardJPARepositoryInterface.save(creditCardJPA).getCreditCardNumber();
	}

}
