package com.legalease.LegalEaseSB;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LegalEaseSbApplication {
	public static void main(String[] args) {

		//#Use the below command to run the project to get the env variables
		//#(To do : edit run configuration in IDE so that i don't have to pass this everytime i run the project)
		//#MONGO_URI=$MONGO_URI ./mvnw spring-boot:run

		SpringApplication.run(LegalEaseSbApplication.class, args);
	}
}
