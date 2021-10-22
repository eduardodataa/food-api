package com.food.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.food.domain.exception.EntidadeEmUsoException;
import com.food.domain.exception.FormaPagamentoNaoEncontradoException;
import com.food.domain.model.FormaPagamento;
import com.food.domain.repository.FormaPagamentoRepository;

@Service
public class CadastroFormaDePagamentoService {
	
	private static final String MSG_FORMA_PAGAMENTO_EM_USO = "FormaDePagamento %d não pode ser removida, pois está em uso";
	@Autowired
	private FormaPagamentoRepository formaPagamentoRepository;

	@Transactional
	public FormaPagamento salvar(FormaPagamento estado) {
		return formaPagamentoRepository.save(estado);
	}

	@Transactional
	public void excluir(Long formaPagamentoId)  {
		try {
			formaPagamentoRepository.deleteById(formaPagamentoId);
			formaPagamentoRepository.flush();
		} catch (EmptyResultDataAccessException e) {
			throw new FormaPagamentoNaoEncontradoException(formaPagamentoId);
		}catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(
					String.format(MSG_FORMA_PAGAMENTO_EM_USO, formaPagamentoId));
		}
	}
	
	public FormaPagamento buscarOuFalhar(Long estadoId) {
		return formaPagamentoRepository.findById(estadoId)
				.orElseThrow( () -> new FormaPagamentoNaoEncontradoException(estadoId));
	}

}
