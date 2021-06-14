package com.food.domain.repository;

import java.util.List;

import com.food.domain.model.Cozinha;

import javassist.NotFoundException;

public interface CozinhaRepository {
	
	List<Cozinha> listar();
	Cozinha buscar(Long id);
	Cozinha salvar(Cozinha cozinha);
	void remover(Cozinha cozinha);
	void remover(Long cozinhaId) throws NotFoundException;

}
