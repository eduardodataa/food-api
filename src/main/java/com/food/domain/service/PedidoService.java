package com.food.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.food.domain.exception.EntidadeEmUsoException;
import com.food.domain.exception.NegocioException;
import com.food.domain.exception.PedidoNaoEncontradoException;
import com.food.domain.model.FormaPagamento;
import com.food.domain.model.Pedido;
import com.food.domain.model.Restaurante;
import com.food.domain.model.Usuario;
import com.food.domain.repository.PedidoRepository;
import com.food.domain.repository.UsuarioRepository;

@Service
public class PedidoService {
	
	private static final String MSG_ESTADO_EM_USO = "PedidoInput de código %d não pode ser removido, pois está em uso";
	private static final String FORMA_PAGAMENTO_NAO_ACEITA = "Forma de pagamento %s não disponível para o restaurante %s";
	@Autowired
	private PedidoRepository pedidoRepository;
	@Autowired
	private UsuarioService usuarioService;
	@Autowired
	private FormaDePagamentoService formaDePagamentoService;
	@Autowired
	private RestauranteService restauranteService;

	@Transactional
	public Pedido salvar(Pedido pedido) {
		Usuario cliente = usuarioService.buscarOuFalhar(pedido.getCliente().getId());
		FormaPagamento formaPagamento = formaDePagamentoService.buscarOuFalhar(pedido.getFormaPagamento().getId());
		Restaurante restaurante = restauranteService.buscarOuFalhar(pedido.getRestaurante().getId());
		if(!restaurante.aceitaFormaPagamento(formaPagamento)) {
			throw new NegocioException(String.format(FORMA_PAGAMENTO_NAO_ACEITA, formaPagamento.getDescricao(), restaurante.getNome()));
		}
		pedido.setCliente(cliente);
		pedido.setRestaurante(restaurante);
		pedido.setFormaPagamento(formaPagamento);
		return pedidoRepository.save(pedido);
	}

	@Transactional
	public void excluir(Long produtoId)  {
		try {
			pedidoRepository.deleteById(produtoId);
		} catch (EmptyResultDataAccessException e) {
			throw new PedidoNaoEncontradoException(produtoId);
		}catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(
					String.format(MSG_ESTADO_EM_USO, produtoId));
		}
	}
	
	public Pedido buscarOuFalhar(Long produtoId) {
		return pedidoRepository.findById(produtoId)
				.orElseThrow( () -> new PedidoNaoEncontradoException(produtoId));
	}

}
