package com.food.api.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.food.domain.model.Restaurante;
import com.food.domain.repository.RestauranteRepository;

@RestController
@RequestMapping("/teste")
public class TesteController {
	
	@Autowired
	RestauranteRepository restauranteRepository;

	@GetMapping
	public List<Restaurante> getAll(){
		return restauranteRepository.findAll();
	}
	
	@GetMapping("/restaurante/por-nome-e-frete")
	public List<Restaurante> porNomeEFrete(String nome, BigDecimal taxaFreteInicial, BigDecimal taxaFreteFinal){
//		return restauranteRepository.consultarPorNomeETaxaInicialEFinal(nome, taxaFreteInicial, taxaFreteFinal);
		return restauranteRepository.consultarPorNomeETaxaInicialEFinalCriteria(nome, taxaFreteInicial, taxaFreteFinal);
	}
	
	@GetMapping("/restaurante/primeiro")
	public Optional<Restaurante> primeiro(){
		return restauranteRepository.buscaPrimeiro();
	}
}
