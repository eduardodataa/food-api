package com.food.infrastructure.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.food.domain.model.Cidade;
import com.food.domain.repository.CidadeRepository;

@Component
public class CidadeRepositoryImpl implements CidadeRepository {


	@PersistenceContext
	private EntityManager em;

	@Override
	public List<Cidade> listar(){
		//JPQL linguagem de consulta JPA
		return em.createQuery("from Cidade", Cidade.class).getResultList();
	}

	@Override
	@Transactional
	public Cidade salvar(Cidade Cidade) {
		return em.merge(Cidade);
	}

	@Override
	public Cidade buscar(Long id) {
		return em.find(Cidade.class, id);
	}

	@Override
	@Transactional
	public void remover(Cidade Cidade) {
		Cidade = buscar(Cidade.getId());
		em.remove(Cidade);
	}
	
}
