package com.ecfghjp.credit.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ecfghjp.credit.controller.domain.CreditCardRequestDTO;
import com.ecfghjp.credit.controller.domain.CreditCardResponseDTO;
import com.ecfghjp.credit.controller.domain.FetchAllTransactionsRequestDTO;
import com.ecfghjp.credit.controller.domain.TransactionRequestDTO;
import com.ecfghjp.credit.controller.domain.TransactionResponseDTO;
import com.ecfghjp.credit.controller.helper.CreditControllerHelper;
import com.ecfghjp.credit.service.CreditCardOperationsService;

@RestController
@RequestMapping("/credit")
public class CreditCardOperationsController {

	private CreditCardOperationsService creditService;

	@Autowired
	public CreditCardOperationsController(CreditCardOperationsService creditService) {
		this.creditService = creditService;
	}

	@PostMapping("/transaction/")
	public TransactionResponseDTO transaction(@RequestBody TransactionRequestDTO withdrawalRequest) {
		TransactionResponseDTO paymentResponseDTO = CreditControllerHelper
				.convertTransactionDomaintoTO(creditService.payment(withdrawalRequest));
		return paymentResponseDTO;
	}

	@PostMapping("/transaction/{credit-card-number}")
	public List<TransactionResponseDTO> fetchAllTransactions(
			@RequestParam(value = "credit-card-number") String creditCardNumber,
			@RequestBody FetchAllTransactionsRequestDTO fetcAllTransactionsRequestDTO) {
		List<TransactionResponseDTO> paymentResponseDTO = CreditControllerHelper
				.convertTransactionListToDTO(creditService.fetchTransactions(fetcAllTransactionsRequestDTO));
		return paymentResponseDTO;
	}

	@PutMapping("/assign/")
	public CreditCardResponseDTO createCreditCard(@RequestBody CreditCardRequestDTO creditCard) {
		String messsage = creditService.assignLimit(creditCard);

		return new CreditCardResponseDTO(messsage);
	}
}
