package com.food.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.food.domain.model.Estado;

public interface EstadoRepository extends JpaRepository<Estado, Long>{
	
//	List<EstadoDTO> listar();
//	EstadoDTO buscar(Long id);
//	EstadoDTO salvar(EstadoDTO estado);
//	void remover(EstadoDTO estado);
//	void remover(Long estadoId) throws EmptyResultDataAccessException;

}
