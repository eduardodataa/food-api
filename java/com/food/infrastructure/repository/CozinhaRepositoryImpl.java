package com.food.infrastructure.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.food.domain.model.Cozinha;
import com.food.domain.repository.CozinhaRepository;

import javassist.NotFoundException;

@Component
public class CozinhaRepositoryImpl implements CozinhaRepository {


	@PersistenceContext
	private EntityManager em;

	@Override
	public List<Cozinha> listar(){
		//JPQL linguagem de consulta JPA
		return em.createQuery("from Cozinha", Cozinha.class).getResultList();
	}

	@Override
	@Transactional
	public Cozinha salvar(Cozinha cozinha) {
		return em.merge(cozinha);
	}

	@Override
	public Cozinha buscar(Long id) {
		return em.find(Cozinha.class, id);
	}

	@Override
	@Transactional
	public void remover(Cozinha cozinha) {
		cozinha = buscar(cozinha.getId());
		em.remove(cozinha);
	}
	
	@Override
	@Transactional
	public void remover(Long cozinhaId) throws NotFoundException {
		Cozinha cozinha = buscar(cozinhaId);
		if(cozinha == null) {
			throw new NotFoundException("Recurso não encontrado");
		}
		em.remove(cozinha);
	}
	
}
