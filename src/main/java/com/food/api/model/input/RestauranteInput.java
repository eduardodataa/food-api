package com.food.api.model.input;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.groups.ConvertGroup;
import javax.validation.groups.Default;

import com.food.Groups;
import com.food.api.model.CozinhaDTO;

import lombok.Data;

@Data
public class RestauranteInput {
	
	
	@NotBlank //não aceita espaços em branco
	private String nome;

	@PositiveOrZero
	@Column(nullable = false)
	private BigDecimal taxaFrete;
	
	@NotNull
	@ConvertGroup(from = Default.class, to = Groups.CozinhaId.class) //converta group default p/ cadastros restaurante.class
	@Valid // valida as propriedades de cozinha
	private CozinhaDTO cozinha;

}
