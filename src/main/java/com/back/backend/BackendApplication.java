package com.back.backend;

import com.back.backend.classes.Card;
import com.back.backend.classes.repo.CardRepository;
import com.back.backend.service.CardService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.sql.Array;

@SpringBootApplication
public class BackendApplication {

	@Autowired
	private CardService cardService;

	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}

	@Bean
	InitializingBean initializeCards() {

		return cardService::initializeCards;
	}

}