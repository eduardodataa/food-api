package com.food.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.food.domain.exception.CidadeNaoEncontradaException;
import com.food.domain.exception.EntidadeEmUsoException;
import com.food.domain.model.Cidade;
import com.food.domain.model.Estado;
import com.food.domain.repository.CidadeRepository;

@Service
public class CadastroCidadeService {
	
	private static final String MSG_CIDADE_EM_USO = "CidadeDTO de código %d não pode ser removida, pois está em uso";
//	private static final String MSG_CIDADE_NAO_ENCONTRADA = "Não existe um cadastro de cidade com código %d";
	@Autowired
	private CidadeRepository cidadeRepository;
	@Autowired
	private CadastroEstadoService cadastroEstadoService;

	@Transactional
	public Cidade salvar(Cidade cidade) {
		Estado estado = cadastroEstadoService.buscarOuFalhar(cidade.getEstado().getId());
		cidade.setEstado(estado);
		return cidadeRepository.save(cidade);
	}

	@Transactional
	public void excluir(Long cidadeId)  {
		try {
			cidadeRepository.deleteById(cidadeId);
		} catch (EmptyResultDataAccessException e) {
			throw new CidadeNaoEncontradaException(cidadeId);
		}catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(
					String.format(MSG_CIDADE_EM_USO, cidadeId));
		}
	}

	public Cidade buscarOuFalhar(Long cidadeId) {
		return cidadeRepository.findById(cidadeId)
			.orElseThrow(() -> new CidadeNaoEncontradaException(cidadeId));
		
	}

}
