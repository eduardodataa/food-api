package com.food.api.exceptionhandler;

import lombok.Getter;

@Getter
public enum ProblemType {
	
	ENTIDADE_NAO_ENCONTRADA ("/entidade-nao-encontrada", "Entidade não encontrada"),
	ERRO_NEGOCIO ("/excecao-negocio", "Violação de regra de negócio"),
	ENTIDADE_EM_USO ("/entidade-em-uso", "Entidade em uso"),
	MENSAGEM_INCOMPREENSIVEL ("/mensagem-inconpreensivel", "Mensagem incompreensivel"),
	PARAMETRO_INVALIDO ("/parametro-invalido", "Parâmetro Inválido");
	
	
	private String uri;
	private String title;
	

	private ProblemType( String uri, String title) {
		this.uri = uri;
		this.title = title;
	}

}
