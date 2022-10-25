package com.planus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;

import java.security.Security;

@SpringBootApplication
@EnableCaching
public class PlanusApplication {

	public static void main(String[] args) {
		SpringApplication.run(PlanusApplication.class, args);
	}

}
