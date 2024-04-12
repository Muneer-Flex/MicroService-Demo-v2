package com.bank.centralauthservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class CentralAuthServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CentralAuthServiceApplication.class, args);
	}

}
