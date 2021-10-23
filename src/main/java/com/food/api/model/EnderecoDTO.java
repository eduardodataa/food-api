package com.food.api.model;

import javax.persistence.Embeddable;

import lombok.Data;

@Data
@Embeddable
public class EnderecoDTO {

	private String cep;

	private String logradouro;

	private String numero;

	private String bairro;

	private String complemento;

	private CidadeResumoDTO cidade;
}
