package com.food.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.food.domain.model.FormaPagamento;

public interface FormaPagamentoRepository extends JpaRepository<FormaPagamento, Long>{
	
//	List<FormaPagamento> listar();
//	FormaPagamento buscar(Long id);
//	FormaPagamento salvar(FormaPagamento cozinha);
//	void remover(FormaPagamento cozinha);

}
