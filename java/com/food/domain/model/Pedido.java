package com.food.domain.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.springframework.data.annotation.CreatedDate;

import lombok.Data;

@Data
@Entity
public class Pedido {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private BigDecimal subtotal;
	private BigDecimal taxaFrete;
	private BigDecimal valorTotal;
	
	@CreatedDate
	private Date dataCriacao;
	private Date dataConfirmacao;
	private Date dataCancelamento;
	private Date dataEntrega;
	
	@ManyToOne
	@JoinColumn
	private FormaPagamento formaPagamento;

	@ManyToOne
	@JoinColumn
	private Restaurante restaurante;

	@ManyToOne
	@JoinColumn
	private Usuario cliente;

	@Embedded
	private Endereco  enderecoEntrega;
	
	@Enumerated
	private StatusPedido status = StatusPedido.CRIADO;
	

	@OneToMany(mappedBy = "pedido")
	private List<ItemPedido> itensPedido;
	
	public enum StatusPedido{
		CRIADO,
		CANCELADO,
		CONFIRMADO,
		ENTREGUE;
	}	

}


