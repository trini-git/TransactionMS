package com.transaction.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import com.transaction.model.TransactionModel;

import reactor.core.publisher.Flux;

@Repository
public interface TransactionRepositoryInterface extends ReactiveMongoRepository<TransactionModel, String>{
	
	//Mono<TransactionModel> findByAccountNumber(String accountNumber);
	Flux<TransactionModel> findByAccountNumber(String accountNumber);
}
