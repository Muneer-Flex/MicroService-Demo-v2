package com.bank.createcustomeraccount;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class CreateCustomerAccountApplication {

	public static void main(String[] args) {
		SpringApplication.run(CreateCustomerAccountApplication.class, args);
	}

}
