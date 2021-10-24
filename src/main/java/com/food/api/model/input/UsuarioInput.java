package com.food.api.model.input;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class UsuarioInput {

	private Long id;
	
	@NotBlank
	private String nome;
	
	@NotBlank
	@Email
	private String email;

}
