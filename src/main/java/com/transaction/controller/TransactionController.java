package com.transaction.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import com.transaction.model.BankAccountModel;
import com.transaction.model.TransactionModel;
import com.transaction.service.TransactionService;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/transaction")
public class TransactionController{
	
	BankAccountModel bankAccountModel = new BankAccountModel();
	
	WebClient client = WebClient.builder().baseUrl("http://localhost:8007/bank-account")
			  .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE).build();
	
	@Autowired
	TransactionService transactionService;
	
	@PostMapping("/insert/{accountNumber}/{typeOperation}/{amount}")
	public Mono<TransactionModel> insertTransaction(@RequestBody TransactionModel transactionModel, @PathVariable String accountNumber, @PathVariable String typeOperation, @PathVariable Double amount){
		
		if (typeOperation.equalsIgnoreCase("D")) {
			//bankAccountModel.setAmount(transactionModel.getAmount());
			updateAmountBankAccount(bankAccountModel,accountNumber,typeOperation,amount).flatMap(dato ->{
				transactionModel.setAmount(dato.getAmount());
				return Mono.just(dato);
				bankAccountModel.setAmount(transactionModel.getAmount());
			});
			return transactionService.insertTransaction(transactionModel);
		}/*else if (typeNumberAccount.equalsIgnoreCase("R")){
			bankAccountModel.setAmount(transactionModel.getAmount() - amount);
			updateAmountBankAccount(bankAccountModel,accountNumber).subscribe();
			return transactionService.insertTransaction(transactionModel);
		}*/
		return Mono.just(transactionModel);	
	}
	
	/*Microservice that connects*/
	public Mono<BankAccountModel> updateAmountBankAccount (
			BankAccountModel bankAccountModel, String accountNumber, String typeOperation, Double accountNumberAmount){
		return client.put().uri("/update-amount/"+accountNumber+"/"+typeOperation+"/"+accountNumberAmount).syncBody(bankAccountModel)
				.retrieve().bodyToMono(BankAccountModel.class);			
	}

}
