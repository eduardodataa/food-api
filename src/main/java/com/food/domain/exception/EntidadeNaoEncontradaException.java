package com.food.domain.exception;

//@ResponseStatus(value = HttpStatus.NOT_FOUND) //, reason = "Mensagem para aparecer no retorno: Entidade não encontrada"
public abstract class EntidadeNaoEncontradaException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public EntidadeNaoEncontradaException(String mensagem) {
		super(mensagem);	
	}
	
	//utilizado quando a exceção estende httpstatusexception (extends ResponseStatusException)
//	public EntidadeNaoEncontradaException(String reason) {
//		this(HttpStatus.NOT_FOUND, reason);
//	}

}
