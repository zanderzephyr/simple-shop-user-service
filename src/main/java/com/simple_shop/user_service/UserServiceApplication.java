package com.simple_shop.item_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public final class ItemServiceApplication {
	public static void main(final String[] args) {
		// Starts the embedded Tomcat server on port 8080
		SpringApplication.run(ItemServiceApplication.class, args);
	}
}
