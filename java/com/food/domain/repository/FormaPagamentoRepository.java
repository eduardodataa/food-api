package com.food.domain.repository;

import java.util.List;

import com.food.domain.model.FormaPagamento;

public interface FormaPagamentoRepository {
	
	List<FormaPagamento> listar();
	FormaPagamento buscar(Long id);
	FormaPagamento salvar(FormaPagamento cozinha);
	void remover(FormaPagamento cozinha);

}
