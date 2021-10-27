package com.food.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.food.domain.model.Produto;

public interface ProdutoRepository extends CustomJpaRepository<Produto, Long>{
	
	@Query("from Produto where restaurante.id = :restauranteId and id = :produtoId ")
	public Optional<Produto> findById(@Param("restauranteId") Long restauranteId, @Param("produtoId") Long produtoId);
	

}
