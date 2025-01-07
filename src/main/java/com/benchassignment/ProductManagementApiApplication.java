package com.benchassignment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class ProductManagementApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProductManagementApiApplication.class, args);
	}

}
