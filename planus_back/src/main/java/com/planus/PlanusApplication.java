package com.planus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableCaching
@EnableJpaAuditing
@PropertySources({
		@PropertySource("classpath:privateKey.properties"),
		@PropertySource("classpath:application.properties"),
})
public class PlanusApplication {

	public static void main(String[] args) {
		SpringApplication.run(PlanusApplication.class, args);
	}

}
