package com.transaction.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import com.transaction.model.BankSavingAccountModel;
import com.transaction.model.TransactionModel;
import com.transaction.service.TransactionService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/transaction")
public class TransactionController {

	@Autowired
	TransactionService transactionService;
	
	/*to see all the movements about a account number*/
	@GetMapping("/get/account-number/{accountNumber}")
	public Flux<TransactionModel> findByAccountNumber(@PathVariable String accountNumber){
		
		return transactionService.findByAccountNumber(accountNumber);
	}
	
	/*to update the amount depends of Operation Type*/
	@PostMapping("/insert/{accountNumber}/{typeOperation}/{amount}")
	public Mono<TransactionModel> insertTransaction(@RequestBody TransactionModel transactionModel,
			@PathVariable String accountNumber, @PathVariable String typeOperation, @PathVariable Double amount) {
		
		return transactionService.insertTransaction(transactionModel,accountNumber,typeOperation,amount);
		
	}
}
