package com.food.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.food.domain.model.Cidade;

public interface CidadeRepository extends JpaRepository<Cidade, Long> {
	
//	List<CidadeDTO> listar();
//	CidadeDTO buscar(Long id);
//	CidadeDTO salvar(CidadeDTO cidade);
//	void remover(CidadeDTO cidade);
//	void remover(Long cidadeId) throws EmptyResultDataAccessException;

}
