package com.hadygust.ecommerce;

import com.hadygust.ecommerce.entity.Product;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EcommerceApiApplication {

	public static void main(String[] args) {

		Product p = new Product();

		SpringApplication.run(EcommerceApiApplication.class, args);
//		System.out.println("Hello spring boot!");
	}

}
