package com.food.domain.exception;

public class UsuarioNaoEncontradoException extends EntidadeNaoEncontradaException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UsuarioNaoEncontradoException(String mensagem) {
		super(mensagem);	
	}
	

	public UsuarioNaoEncontradoException(Long estadoId) {
		this(String.format("Não existe um cadastro de cidade com código %d", estadoId));	
	}
	

}
