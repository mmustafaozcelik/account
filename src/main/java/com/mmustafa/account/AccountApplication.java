package com.mmustafa.account;

import com.mmustafa.account.model.Customer;
import com.mmustafa.account.repository.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.HashSet;
import java.util.concurrent.Callable;

@SpringBootApplication
public class AccountApplication implements CommandLineRunner {
	private final CustomerRepository customerRepository;

	public AccountApplication(CustomerRepository customerRepository) {
		this.customerRepository = customerRepository;
	}

	public static void main(String[] args) {
		SpringApplication.run(AccountApplication.class, args

		);
	}


	@Override
	public void run(String... args) throws Exception {
		Customer customer= customerRepository.save(new Customer("", "Mustafa" , "Özçelik" , new HashSet<>()));
		System.out.println(customer.getId());
	}
}
