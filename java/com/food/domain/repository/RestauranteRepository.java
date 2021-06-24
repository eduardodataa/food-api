package com.food.domain.repository;

import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.jpa.repository.JpaRepository;

import com.food.domain.model.Estado;
import com.food.domain.model.Restaurante;

public interface RestauranteRepository extends JpaRepository<Restaurante, Long>{
	
//	List<Restaurante> listar();
//	Restaurante buscar(Long id);
//	Restaurante salvar(Restaurante cozinha);
//	void remover(Long restauranteId) throws EmptyResultDataAccessException;

}
