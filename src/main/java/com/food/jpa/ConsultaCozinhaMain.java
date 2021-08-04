package com.food.jpa;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import com.food.FoodApiApplication;
import com.food.domain.model.Cozinha;
import com.food.domain.repository.CozinhaRepository;

public class ConsultaCozinhaMain {

	public static void main(String[] args) {
		
		//esse método inicia e para a aplicação
		//serve para realizar testes pontuais
		ApplicationContext context = new SpringApplicationBuilder(FoodApiApplication.class)
				.web(WebApplicationType.NONE)
				.run(args);
		
		CozinhaRepository cozinhaRepository = context.getBean(CozinhaRepository.class);
		
		System.out.println(cozinhaRepository.save(new Cozinha("Brasileira")));
		Cozinha c = new Cozinha("Japosesa");
		c = cozinhaRepository.save(c);
		
		cozinhaRepository.delete(c);
		
		
		
		
	}
}
