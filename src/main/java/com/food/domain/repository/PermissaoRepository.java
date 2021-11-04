package com.food.domain.repository;

import org.springframework.stereotype.Repository;

import com.food.domain.model.Permissao;

@Repository
public interface PermissaoRepository extends CustomJpaRepository<Permissao, Long>{
	

}
