package com.transaction.service;

import com.transaction.model.TransactionModel;

import reactor.core.publisher.Mono;

public interface TransactionServiceInterface {
	
	Mono<TransactionModel> insertTransaction (TransactionModel transactionModel);
}
