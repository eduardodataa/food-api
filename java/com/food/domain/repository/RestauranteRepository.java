package com.food.domain.repository;

import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;

import com.food.domain.model.Restaurante;

public interface RestauranteRepository {
	
	List<Restaurante> listar();
	Restaurante buscar(Long id);
	Restaurante salvar(Restaurante cozinha);
	void remover(Long restauranteId) throws EmptyResultDataAccessException;

}
