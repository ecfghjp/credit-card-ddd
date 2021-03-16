package com.ecfghjp.credit.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.ecfghjp.credit.exception.CreditLimitAlreadyRegistered;
import com.ecfghjp.credit.exception.NotEnoughCreditException;
import com.ecfghjp.credit.exception.TransactionInLastHourException;
import com.ecfghjp.credit.service.CreditCardOperationsService;
import com.ecfghjp.credit.service.domain.CreditCardConstants;
import com.ecfghjp.credit.service.domain.CreditCardTransaction;
import com.ecfghjp.credit.service.domain.TransactionId;
import com.ecfghjp.credit.service.domain.TransactionPurpose;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = CreditCardOperationsController.class)
public class CreditCardOperationsControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private CreditCardOperationsService creditCardOperationsService;

	@Test
	public void paymentRequest_paymentSuccess() throws Exception {

		CreditCardTransaction creditCardTransaction = new CreditCardTransaction(new BigDecimal(500),
				TransactionPurpose.PAYMENT, LocalDateTime.now(), new BigDecimal(1000), new BigDecimal(500));
		creditCardTransaction.addTransactionId(new TransactionId("transactionid1234"));
		when(creditCardOperationsService.payment(any())).thenReturn(creditCardTransaction);

		mockMvc.perform(MockMvcRequestBuilders.post("/credit/transaction/").content(
				"{\"creditCardNumber\": \"100100100100\" ,\"transactionAmount\": 5000.0},\"transactionPurpose\": \"PAYMENT\"")
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("transactionId").value("transactionid1234"))
				.andExpect(jsonPath("transactionAmount").value(new BigDecimal(500)))
				.andExpect(jsonPath("remainingCreditAmount").value(new BigDecimal(500)));
	}

	@Test
	public void withdrawalRequest_NotEnoughCredit() throws Exception {
		when(creditCardOperationsService.payment(any())).thenThrow(new NotEnoughCreditException());

		mockMvc.perform(MockMvcRequestBuilders.post("/credit/transaction/").content(
				"{\"creditCardNumber\": \"100100100100\" ,\"transactionAmount\": 5000.0},\"transactionPurpose\": \"PAYMENT\"")
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isInternalServerError());

	}

	@Test
	public void assignLimit_LimitAssigned() throws Exception {

		when(creditCardOperationsService.assignLimit(any())).thenReturn("LIMIT_ASSIGNED");

		mockMvc.perform(MockMvcRequestBuilders.put("/credit/assign/")
				.content("{\"creditCardNumber\": \"100100100100\" ,\"limitAssigned\": 5000.0}")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("message").value(CreditCardConstants.LIMIT_ASSIGNED));

	}

	@Test
	public void assignbLimit_Limit_Already_Assigned() throws Exception {
		when(creditCardOperationsService.assignLimit(any())).thenThrow(new CreditLimitAlreadyRegistered());

		mockMvc.perform(MockMvcRequestBuilders.put("/credit/assign/")
				.content("{\"creditCardNumber\": \"100100100100\" ,\"limitAssigned\": 5000.0}")
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isConflict());

	}

	@Test
	public void withdrawalRequest_FailWithWithdrawalInLastHour() throws Exception {
		when(creditCardOperationsService.payment(any())).thenThrow(new TransactionInLastHourException());

		mockMvc.perform(MockMvcRequestBuilders.post("/credit/transaction/").content(
				"{\"creditCardNumber\": \"100100100100\" ,\"transactionAmount\": 5000.0},\"transactionPurpose\": \"PAYMENT\"")
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isInternalServerError());

	}

	@Test
	public void get_all_transactions_for_credit_card_success() throws Exception {
		// setup
		List<CreditCardTransaction> creditCardTransactions = new ArrayList<CreditCardTransaction>();
		CreditCardTransaction creditCardTransaction = new CreditCardTransaction(new BigDecimal(500),
				TransactionPurpose.PAYMENT, LocalDateTime.of(2020, Month.OCTOBER, 13, 10, 10, 30), new BigDecimal(1500),
				new BigDecimal(1000));
		creditCardTransaction.addTransactionId(new TransactionId("transaction0"));
		creditCardTransactions.add(creditCardTransaction);
		when(creditCardOperationsService.fetchTransactions(any())).thenReturn(creditCardTransactions);

		// act and assert
		mockMvc.perform(MockMvcRequestBuilders.post("/credit/transaction/100100100100/")
				.content("{\"fromDate\": \"12/10/2020\",\"toDate\":\"12/11/2020\"}")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$[0].transactionId").value("transaction0"))
				.andExpect(jsonPath("$[0].trannsactionAmount").value(500.0))
				.andExpect(jsonPath("$[0].transactionDate").value("13/10/2020"))
				.andExpect(jsonPath("$[0].remainingCredit").value(1000));

	}

	@Test
	public void get_all_transactions_for_credit_card__credit_card_not_present() throws Exception {
		// setup
		// act
		// assert
	}

	@Test
	public void get_all_transactions_for_credit_card__no_transactions() throws Exception {
		// setup
		// act
		// assert
	}

}
