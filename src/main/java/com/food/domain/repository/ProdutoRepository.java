package com.food.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.food.domain.model.Produto;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long>{
	
	@Query("from Produto where restaurante.id = :restaurante and id = :produto")
	public Optional<Produto> findById(@Param("restaurante") Long restauranteId, @Param("produto") Long produtoId);
	

}
