package com.transaction.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.reactive.function.client.WebClient;

import com.transaction.model.BankSavingAccountModel;
import com.transaction.model.TransactionModel;
import com.transaction.repository.TransactionRepositoryInterface;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class TransactionService implements TransactionServiceInterface {
	
	@Autowired
	WebClient client;
	
	@Autowired
	TransactionRepositoryInterface transactionRepositoryInterface;
	
	BankSavingAccountModel bankSavingAccountModel = new BankSavingAccountModel();
	
	/*to see all the movements about a account number*/
	public Flux<TransactionModel> findByAccountNumber(String accountNumber){
		
		return transactionRepositoryInterface.findByAccountNumber(accountNumber);
		
	}
		
	@Override
	public Mono<TransactionModel> insertTransaction(TransactionModel transactionModel) {
		
		bankSavingAccountModel.setAccountNumber(transactionModel.getAccountNumber());
		bankSavingAccountModel.setAmount(transactionModel.getAmount());
		
		if (transactionModel.getTypeOperation().equalsIgnoreCase("R")) {
			return updateAmountRetire(bankSavingAccountModel)
					.flatMap(account -> {
						return transactionRepositoryInterface.save(transactionModel);
					});			
		}else if (transactionModel.getTypeOperation().equalsIgnoreCase("D")) {
			updateAmountDeposite(bankSavingAccountModel).subscribe();
			return transactionRepositoryInterface.save(transactionModel);
		}
		return Mono.empty();
		
	}
		
	/* Microservice that connects */
	public Mono<BankSavingAccountModel> updateAmountRetire(BankSavingAccountModel bankSavingAccountModel) {
		return client.put()
				.uri("/update-retire")
				.accept(MediaType.APPLICATION_JSON_UTF8)
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.syncBody(bankSavingAccountModel)
				.retrieve()
				.bodyToMono(BankSavingAccountModel.class)
				.switchIfEmpty(Mono.empty());
		
	}
	
	
	/* Microservice that connects */
	public Mono<BankSavingAccountModel> updateAmountDeposite(BankSavingAccountModel bankSavingAccountModela) {
		return client.put()
				.uri("/update-deposite/")
				.accept(MediaType.APPLICATION_JSON_UTF8)
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.syncBody(bankSavingAccountModel)
				.retrieve()
				.bodyToMono(BankSavingAccountModel.class)
				.switchIfEmpty(Mono.empty());
	}
	
}
