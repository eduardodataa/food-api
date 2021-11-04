package com.food.domain.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.food.domain.exception.PermissaoNaoEncontradaException;
import com.food.domain.model.Grupo;
import com.food.domain.model.Permissao;
import com.food.domain.repository.PermissaoRepository;

@Service
public class PermissaoService {
	
	@Autowired
	GrupoService grupoService;

	@Autowired
	PermissaoService permissaoService;
	
	@Autowired
	PermissaoRepository permissaoRepository;

	@Transactional
	public void associarFormaPagamento(Long grupoId, Long permissaoId) {
		Grupo grupo = grupoService.buscarOuFalhar(grupoId);
		Permissao permissao = buscarOuFalhar(permissaoId);
		grupo.getPermissoes().add(permissao);
		
	}

	private Permissao buscarOuFalhar(Long permissaoId) {
		return permissaoRepository.findById(permissaoId)
			.orElseThrow( () -> new PermissaoNaoEncontradaException(permissaoId));
	}

	@Transactional
	public void desassociarFormaPagamento(Long grupoId, Long permissaoId) {
		Grupo grupo = grupoService.buscarOuFalhar(grupoId);
		Permissao permissao = buscarOuFalhar(permissaoId);
		grupo.getPermissoes().remove(permissao);
		
	}
}
