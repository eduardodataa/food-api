package com.food.api.model;

import lombok.Data;


@Data
public class CidadeDTO {
	
	private Long id;
	
	private String nome;
	
	private EstadoDTO estadoDTO;
}
