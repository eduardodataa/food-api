package com.food.domain.repository;

import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.jpa.repository.JpaRepository;

import com.food.domain.model.Estado;

public interface EstadoRepository extends JpaRepository<Estado, Long>{
	
//	List<Estado> listar();
//	Estado buscar(Long id);
//	Estado salvar(Estado estado);
//	void remover(Estado estado);
//	void remover(Long estadoId) throws EmptyResultDataAccessException;

}
