package com.food.api.model;

import java.time.OffsetDateTime;

import lombok.Data;

@Data
public class UsuarioDTO {


	private Long id;

	private String nome;
	
	private String email;
	
	private OffsetDateTime dataCadastro;
	
//	private List<Grupo> grupos;
}
