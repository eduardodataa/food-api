package com.food.domain.exception;

public class PedidoNaoEncontradoException extends EntidadeNaoEncontradaException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PedidoNaoEncontradoException(String mensagem) {
		super(mensagem);	
	}
	

	public PedidoNaoEncontradoException(Long permissaoId) {
		this(String.format("Não existe um cadastro de permissao com código %d", permissaoId));	
	}
	

}
