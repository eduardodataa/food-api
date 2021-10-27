package com.food.domain.exception;

public class ProdutoNaoEncontradoException extends EntidadeNaoEncontradaException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ProdutoNaoEncontradoException(String mensagem) {
		super(mensagem);	
	}
	

	public ProdutoNaoEncontradoException(Long estadoId) {
		this(String.format("Não existe um cadastro de produto com código %d", estadoId));	
	}
	

}
