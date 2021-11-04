package com.food.api.model;

import java.math.BigDecimal;

import lombok.Data;

/**
 * É o restaurante Model
 * @author duduc
 *
 */

@Data
public class RestauranteDTO {
	private Long id;

	private String nome;

	private BigDecimal taxaFrete;
	
	/*
	 * retorna objeto aninhado
	 */
	private CozinhaDTO cozinha;
	private Boolean ativo;
	private Boolean aberto;
	private EnderecoDTO endereco;

}
