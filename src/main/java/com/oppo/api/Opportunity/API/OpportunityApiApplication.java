package com.oppo.api.Opportunity.API;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class OpportunityApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(OpportunityApiApplication.class, args);
	}

}
