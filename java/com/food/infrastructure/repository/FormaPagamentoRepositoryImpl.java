package com.food.infrastructure.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.food.domain.model.FormaPagamento;
import com.food.domain.repository.FormaPagamentoRepository;

@Repository
public class FormaPagamentoRepositoryImpl implements FormaPagamentoRepository {


	@PersistenceContext
	private EntityManager em;

	@Override
	public List<FormaPagamento> listar(){
		//JPQL linguagem de consulta JPA
		return em.createQuery("from FormaPagamento", FormaPagamento.class).getResultList();
	}

	@Override
	@Transactional
	public FormaPagamento salvar(FormaPagamento FormaPagamento) {
		return em.merge(FormaPagamento);
	}

	@Override
	public FormaPagamento buscar(Long id) {
		return em.find(FormaPagamento.class, id);
	}

	@Override
	@Transactional
	public void remover(FormaPagamento FormaPagamento) {
		FormaPagamento = buscar(FormaPagamento.getId());
		em.remove(FormaPagamento);
	}
	
}
