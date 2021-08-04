package com.food.domain.model;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Data;

@Data
@Entity
public class ItemPedido {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private int quantidade;
	private BigDecimal precoUnitario;
	private BigDecimal precoTotal;
	private String observacao;

	@ManyToOne
	@JoinColumn
	private Produto produto;
	
	@ManyToOne
	@JoinColumn
	private Pedido pedido;
	

}
