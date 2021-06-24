package com.food.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.food.domain.model.Cidade;

public interface CidadeRepository extends JpaRepository<Cidade, Long> {
	
//	List<Cidade> listar();
//	Cidade buscar(Long id);
//	Cidade salvar(Cidade cidade);
//	void remover(Cidade cidade);
//	void remover(Long cidadeId) throws EmptyResultDataAccessException;

}
