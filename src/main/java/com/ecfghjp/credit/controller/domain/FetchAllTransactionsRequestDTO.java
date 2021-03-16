package com.ecfghjp.credit.controller.domain;

import java.time.LocalDateTime;

public class FetchAllTransactionsRequestDTO {
	private LocalDateTime fromDate;
	private LocalDateTime toDate;

	private FetchAllTransactionsRequestDTO() {
	}

	private FetchAllTransactionsRequestDTO(LocalDateTime fromDate, LocalDateTime toDate, String month) {
		this.fromDate = fromDate;
		this.toDate = toDate;
	}

	public LocalDateTime getFromDate() {
		return fromDate;
	}

	public LocalDateTime getToDate() {
		return toDate;
	}

}
