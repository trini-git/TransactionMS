package com.transaction;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class TransactionMsApplication {

	public static void main(String[] args) {
		SpringApplication.run(TransactionMsApplication.class, args);
	}

}
