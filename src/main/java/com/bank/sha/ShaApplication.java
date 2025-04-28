package com.bank.sha;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class ShaApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShaApplication.class, args);
	}

}
