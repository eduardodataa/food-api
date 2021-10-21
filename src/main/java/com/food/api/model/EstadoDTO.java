package com.food.api.model;

import javax.persistence.Entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class EstadoDTO {
	
	private Long id;
	
	private String nome;
}
