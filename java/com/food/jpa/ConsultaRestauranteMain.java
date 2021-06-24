package com.food.jpa;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import com.food.FoodApiApplication;
import com.food.domain.repository.RestauranteRepository;

public class ConsultaRestauranteMain {

	public static void main(String[] args) {
		
		//esse método inicia e para a aplicação
		//serve para realizar testes pontuais
		ApplicationContext context = new SpringApplicationBuilder(FoodApiApplication.class)
				.web(WebApplicationType.NONE)
				.run(args);
		
		RestauranteRepository restauranteRepository = context.getBean(RestauranteRepository.class);
		
		restauranteRepository.findAll().stream().forEach(r -> System.out.println(r.getNome() + " - cozinha: " + r.getCozinha().getNome() + " - Frete:" + r.getTaxaFrete()));
		
//		System.out.println(RestauranteRepository.salvar(new Restaurante(null, "La Boheme", null, null)));
//		Restaurante c = new Restaurante(null, "Artuzi", null, null);
//		c = RestauranteRepository.salvar(c);
//		
//		RestauranteRepository.remover(c);
		
		
		
		
	}
}
