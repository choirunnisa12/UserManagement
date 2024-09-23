package com.example.UserManagementt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class UserManagementtApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserManagementtApplication.class, args);
	}

}
