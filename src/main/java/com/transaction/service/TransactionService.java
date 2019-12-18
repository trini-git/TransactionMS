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
	
	@Override
	public Mono<TransactionModel> insertTransaction(TransactionModel transactionModel, String accountNumber, String typeOperation, Double amount) {
		
		if (typeOperation.equalsIgnoreCase("D")) {
			
			bankSavingAccountModel.setAmount(transactionModel.getAmount());
			updateAmountBankAccount(bankSavingAccountModel, accountNumber, typeOperation, amount).subscribe();
			return transactionRepositoryInterface.save(transactionModel);
		} else if (typeOperation.equalsIgnoreCase("R")) {
			if (bankSavingAccountModel.getAmount() >= transactionModel.getAmount()) {
				bankSavingAccountModel.setAmount(transactionModel.getAmount());
				updateAmountBankAccount(bankSavingAccountModel, accountNumber, typeOperation, amount).subscribe();
				return transactionRepositoryInterface.save(transactionModel);
			}
		}
		return Mono.empty();
				
	}
	
	/*to see all the movements about a account number*/
	public Flux<TransactionModel> findByAccountNumber(String accountNumber){
		
		return transactionRepositoryInterface.findByAccountNumber(accountNumber);
		
	}
	
	/* Microservice that connects */
	public Mono<BankSavingAccountModel> updateAmountBankAccount(BankSavingAccountModel bankSavingAccountModel, String accountNumber,
			String typeOperation, Double accountNumberAmount) {
		return client.put()
				.uri("/update-amount/" + accountNumber + "/" + typeOperation + "/" + accountNumberAmount)
				.accept(MediaType.APPLICATION_JSON_UTF8)
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.syncBody(bankSavingAccountModel)
				.retrieve()
				.bodyToMono(BankSavingAccountModel.class);
	}
	
}
