package com.example.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.demo.repository.BankJdbcRepository;

@SpringBootApplication
public class SpringBootJdbcBankAppApplication  implements CommandLineRunner {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	BankJdbcRepository repository;

	public static void main(String[] args)  {
		SpringApplication.run(SpringBootJdbcBankAppApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		logger.info("All accounts users 1 -> {}", repository.getBankAccounts());
		logger.info(" Account Info -> {}", repository.findBankAccount(1L));

	}
}
