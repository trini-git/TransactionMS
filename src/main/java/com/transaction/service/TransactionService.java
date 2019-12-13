package com.transaction.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.transaction.model.TransactionModel;
import com.transaction.repository.TransactionRepositoryInterface;

import reactor.core.publisher.Mono;

@Service
public class TransactionService implements TransactionServiceInterface {

	@Autowired
	TransactionRepositoryInterface transactionRepositoryInterface;
	
	@Override
	public Mono<TransactionModel> insertTransaction(TransactionModel transactionModel) {
		
		return transactionRepositoryInterface.save(transactionModel);
				
	}
	
	/*@Override
	public Mono<TransactionModel> insertTransactionTypeOperaction(TransactionModel transactionModel, String typeOperaction, Double amount) {
		
		String typeOperaction = transactionModel.getTypeOperation();
		Double totalAmount = transactionModel.getTotalAmount();
		Double amount = transactionModel.getAmount();
		
		return transactionRepositoryInterface.save(transactionModel);
				
	}*/

}
