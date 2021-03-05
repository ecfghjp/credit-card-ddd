package com.ecfghjp.credit.controller.helper;

import com.ecfghjp.credit.controller.domain.PaymentResponseDTO;
import com.ecfghjp.credit.service.domain.CreditCardTransaction;

public final class CreditControllerHelper {

	
	private CreditControllerHelper() {
	}

	public static PaymentResponseDTO convertWithddrawalVOtoTO(CreditCardTransaction creditCardTransaction) {
		return new PaymentResponseDTO(creditCardTransaction.getTransactionId().getId(),
				creditCardTransaction.getTransactionAmount(), creditCardTransaction.creditLeftAfterTransaction());

	}

}
