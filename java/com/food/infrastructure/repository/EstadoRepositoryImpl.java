package com.food.infrastructure.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.food.domain.model.Estado;
import com.food.domain.repository.EstadoRepository;

@Repository
public class EstadoRepositoryImpl implements EstadoRepository {


	@PersistenceContext
	private EntityManager em;

	@Override
	public List<Estado> listar(){
		//JPQL linguagem de consulta JPA
		return em.createQuery("from Estado", Estado.class).getResultList();
	}

	@Override
	@Transactional
	public Estado salvar(Estado Estado) {
		return em.merge(Estado);
	}

	@Override
	public Estado buscar(Long id) {
		return em.find(Estado.class, id);
	}

	@Override
	@Transactional
	public void remover(Estado Estado) {
		Estado = buscar(Estado.getId());
		em.remove(Estado);
	}

	@Override
	public void remover(Long estadoId) throws EmptyResultDataAccessException {
		Estado estado = buscar(estadoId);
		if(estado == null) {
			throw new EmptyResultDataAccessException(1);
		}
		em.remove(estado);
	}
	
}
