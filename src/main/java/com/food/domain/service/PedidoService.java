package com.food.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.food.domain.exception.EntidadeEmUsoException;
import com.food.domain.exception.PedidoNaoEncontradoException;
import com.food.domain.model.Pedido;
import com.food.domain.repository.PedidoRepository;

@Service
public class PedidoService {
	
	private static final String MSG_ESTADO_EM_USO = "PedidoInput de código %d não pode ser removida, pois está em uso";
	@Autowired
	private PedidoRepository produtoRepository;

	@Transactional
	public Pedido salvar(Pedido produto) {
		return produtoRepository.save(produto);
	}

	@Transactional
	public void excluir(Long produtoId)  {
		try {
			produtoRepository.deleteById(produtoId);
		} catch (EmptyResultDataAccessException e) {
			throw new PedidoNaoEncontradoException(produtoId);
		}catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(
					String.format(MSG_ESTADO_EM_USO, produtoId));
		}
	}
	
	public Pedido buscarOuFalhar(Long produtoId) {
		return produtoRepository.findById(produtoId)
				.orElseThrow( () -> new PedidoNaoEncontradoException(produtoId));
	}

}
