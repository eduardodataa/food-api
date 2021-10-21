package com.food.api.model;

import javax.persistence.Entity;

import lombok.Data;


@Data
@Entity
public class CidadeDTO {
	
	private Long id;
	
	private String nome;
	
	private EstadoDTO estadoDTO;
}
