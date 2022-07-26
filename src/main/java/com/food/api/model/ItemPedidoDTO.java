package com.food.api.model;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Data;

@Data
public class ItemPedidoDTO {
	
	private Long produtoId;
	private String produtoNome;
	
	private int quantidade;
	private BigDecimal precoUnitario;
	private BigDecimal precoTotal;
	private String observacao;

//	private ProdutoDTO produto;
	
}
