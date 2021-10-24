package com.food.infrastructure.repository;

import java.util.Optional;

import javax.persistence.EntityManager;

import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import com.food.domain.repository.CustomJpaRepository;

public class CustomJpaRepositoryImpl<T, ID> extends SimpleJpaRepository<T, ID> implements CustomJpaRepository<T, ID>{

	
	private EntityManager entityManager;

	/**
	 * 
	 * @param entityInformation
	 * @param entityManager
	 */
	public CustomJpaRepositoryImpl(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {
		super(entityInformation, entityManager);
		this.entityManager = entityManager;
		// TODO Auto-generated constructor stub
	}

	/**
	 * Retorna o primeiro registro da tabela de maneira dinâmica
	 */
	@Override
	public Optional<T> buscaPrimeiro() {
		var jpql = "from " + getDomainClass().getName();
		
		T entity = entityManager.createQuery(jpql, getDomainClass()).setMaxResults(1).getSingleResult();
		return Optional.ofNullable(entity);
	}

	@Override
	public void detach(T e) {
		entityManager.detach(e);
		
	}

	
	
}
