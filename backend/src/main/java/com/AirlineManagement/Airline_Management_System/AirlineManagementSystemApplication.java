package com.AirlineManagement.Airline_Management_System;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
@EnableScheduling
@SpringBootApplication
@EnableTransactionManagement
public class AirlineManagementSystemApplication {
	public static void main(String[] args) {
		SpringApplication.run(AirlineManagementSystemApplication.class, args);
	}

}
