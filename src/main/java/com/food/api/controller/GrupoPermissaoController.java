package com.food.api.controller;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.food.api.model.PermissaoDTO;
import com.food.domain.model.Grupo;
import com.food.domain.model.Permissao;
import com.food.domain.service.GrupoService;
import com.food.domain.service.PermissaoService;

@RestController
@RequestMapping("/grupos/{grupoId}/permissoes")
public class GrupoPermissaoController {

	@Autowired
	private GrupoService grupoService;
	
	@Autowired
	private PermissaoService permissaoService;

	@Autowired
	private ModelMapper modelMapper;
	
	@GetMapping
	public List<PermissaoDTO> listar(@PathVariable Long grupoId) {
		Grupo grupo = grupoService.buscarOuFalhar(grupoId);
		return gruposToGruposDTO(grupo.getPermissoes());
	}
	
	@DeleteMapping("/{permissaoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void desassociarFormaPagamento(@PathVariable Long grupoId, @PathVariable Long permissaoId) {
		permissaoService.desassociarFormaPagamento(grupoId, permissaoId);
	}
	
	@PutMapping("/{permissaoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void associarFormaPagamento(@PathVariable Long grupoId, @PathVariable Long permissaoId) {
		permissaoService.associarFormaPagamento(grupoId, permissaoId);
	}
	
	private List<PermissaoDTO> gruposToGruposDTO(Collection<Permissao> listaPermissoes) {
		return listaPermissoes.stream().map(permissao -> grupoToGrupoDTO(permissao)).collect(Collectors.toList());
	}

	private PermissaoDTO grupoToGrupoDTO(Permissao permissao) {
		return modelMapper.map(permissao, PermissaoDTO.class);
	}
}
