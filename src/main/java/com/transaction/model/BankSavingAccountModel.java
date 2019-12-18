package com.transaction.model;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "bank_account")
public class BankSavingAccountModel {

	private Double amount;

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

}
