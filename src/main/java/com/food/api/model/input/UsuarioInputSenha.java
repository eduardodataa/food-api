package com.food.api.model.input;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class UsuarioInputSenha {


	private Long id;
	
	@NotBlank
	private String senhaAtual;
	
	@NotBlank
	private String novaSenha;
}
