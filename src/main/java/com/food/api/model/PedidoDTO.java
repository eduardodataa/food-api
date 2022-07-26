/**
 * 
 */
package com.food.api.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.food.domain.model.FormaPagamento;
import com.food.domain.model.Pedido.StatusPedido;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PedidoDTO {

	private Long id;
	private BigDecimal subtotal;
	private BigDecimal taxaFrete;
	private BigDecimal valorTotal;
	
	private Date dataCriacao;
	private Date dataConfirmacao;
	private Date dataCancelamento;
	private Date dataEntrega;
	
	private FormaPagamento formaPagamento;

	private RestauranteResumoDTO restaurante;

	private UsuarioDTO cliente;

	private EnderecoDTO  enderecoEntrega;
	
	private StatusPedido status = StatusPedido.CRIADO;
	

	private List<ItemPedidoDTO> itensPedido;
}
