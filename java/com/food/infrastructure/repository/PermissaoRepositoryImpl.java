package com.food.infrastructure.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.food.domain.model.Permissao;
import com.food.domain.repository.PermissaoRepository;

@Repository
public class PermissaoRepositoryImpl implements PermissaoRepository {


	@PersistenceContext
	private EntityManager em;

	@Override
	public List<Permissao> listar(){
		//JPQL linguagem de consulta JPA
		return em.createQuery("from Permissao", Permissao.class).getResultList();
	}

	@Override
	@Transactional
	public Permissao salvar(Permissao Permissao) {
		return em.merge(Permissao);
	}

	@Override
	public Permissao buscar(Long id) {
		return em.find(Permissao.class, id);
	}

	@Override
	@Transactional
	public void remover(Permissao Permissao) {
		Permissao = buscar(Permissao.getId());
		em.remove(Permissao);
	}
	
}
