package com.ecfghjp.credit.controller.helper;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import com.ecfghjp.credit.controller.domain.TransactionResponseDTO;
import com.ecfghjp.credit.service.domain.CreditCardTransaction;

public final class CreditControllerHelper {

	
	private CreditControllerHelper() {
	}

	public static TransactionResponseDTO convertTransactionDomaintoTO(CreditCardTransaction creditCardTransaction) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/YYYY");

		return new TransactionResponseDTO(creditCardTransaction.getTransactionId().getId(),
				creditCardTransaction.getTransactionAmount(), creditCardTransaction.creditLeftAfterTransaction(),creditCardTransaction.getTransactionDate().format(formatter));

	}

	public static List<TransactionResponseDTO> convertTransactionListToDTO(List<CreditCardTransaction> transactions) {
		// TODO Auto-generated method stub
		return transactions.stream()
				.map(transaction->convertTransactionDomaintoTO(transaction))
				.collect(Collectors.toList());
	}

}
