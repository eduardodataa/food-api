package com.food.infrastructure.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.food.domain.model.Restaurante;
import com.food.domain.repository.RestauranteRepository;

@Repository
public class RestauranteRepositoryImpl implements RestauranteRepository {


	@PersistenceContext
	private EntityManager em;

	@Override
	public List<Restaurante> listar(){
		//JPQL linguagem de consulta JPA
		return em.createQuery("from Restaurante", Restaurante.class).getResultList();
	}

	@Override
	@Transactional
	public Restaurante salvar(Restaurante cozinha) {
		return em.merge(cozinha);
	}

	@Override
	public Restaurante buscar(Long id) {
		return em.find(Restaurante.class, id);
	}

	@Override
	@Transactional
	public void remover(Long restauranteId) throws EmptyResultDataAccessException {
		Restaurante restaurante = buscar(restauranteId);
		if(restaurante == null) {
			throw new EmptyResultDataAccessException(1);
		}
		em.remove(restaurante);
	}
	
}
