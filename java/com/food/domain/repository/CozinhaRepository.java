package com.food.domain.repository;

import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;

import com.food.domain.model.Cozinha;

public interface CozinhaRepository {
	
	List<Cozinha> listar();
	Cozinha buscar(Long id);
	Cozinha salvar(Cozinha cozinha);
	void remover(Cozinha cozinha);
	void remover(Long cozinhaId) throws EmptyResultDataAccessException;

}
