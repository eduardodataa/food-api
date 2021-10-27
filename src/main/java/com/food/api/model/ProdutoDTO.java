package com.food.api.model;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.PositiveOrZero;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProdutoDTO {
	
	
	@NotBlank
	private String nome;
	
	@NotBlank
	private String descricao;

	@PositiveOrZero
	private BigDecimal preco;

	@NotEmpty
	private boolean ativo;

}
