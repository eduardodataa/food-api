package com.food.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.food.domain.model.Pedido;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long>{
	
	@Query("from Pedido where restaurante.id = :restaurante and id = :pedido")
	public Optional<Pedido> findById(@Param("restaurante") Long restauranteId, @Param("pedido") Long pedidoId);
	

}
