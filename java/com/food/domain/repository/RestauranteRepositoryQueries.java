package com.food.domain.repository;

import java.math.BigDecimal;
import java.util.List;

import com.food.domain.model.Restaurante;

public interface RestauranteRepositoryQueries {

	public List<Restaurante> consultarPorNomeETaxaInicialEFinal(String nome, BigDecimal taxaFreteInicial, BigDecimal taxaFreteFinal);

	List<Restaurante> consultarPorNomeETaxaInicialEFinalCriteria(String nome, BigDecimal taxaFreteInicial,
			BigDecimal taxaFreteFinal);

}