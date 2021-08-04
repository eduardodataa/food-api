package com.food.jpa;

import java.util.List;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import com.food.FoodApiApplication;
import com.food.domain.model.Cozinha;
import com.food.domain.repository.CozinhaRepository;

public class InclusaoCozinhaMain {

	public static void main(String[] args) {
		
		//esse método inicia e para a aplicação
		//serve para realizar testes pontuais
		ApplicationContext context = new SpringApplicationBuilder(FoodApiApplication.class)
				.web(WebApplicationType.NONE)
				.run(args);
		
		CozinhaRepository cozinhaRepository = context.getBean(CozinhaRepository.class);
		
		List<Cozinha> listaCozinhas = cozinhaRepository.findAll();
		
		for (Cozinha cozinha : listaCozinhas) {
			System.out.println(cozinha.getNome());
		}
	}
}
