package com.food.api.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class PermissaoDTO {
	
	private Long id;
	
	private String nome;
	private String descricao;
}
