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

import com.food.api.model.GrupoDTO;
import com.food.domain.model.Grupo;
import com.food.domain.model.Usuario;
import com.food.domain.service.UsuarioService;

@RestController
@RequestMapping("usuarios/{usuarioId}/grupos")
public class UsuarioGrupoController {

	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	private ModelMapper modelMapper;
	
	
	
	@GetMapping
	public List<GrupoDTO> listar(@PathVariable Long usuarioId) {
		Usuario usuario = usuarioService.buscarOuFalhar(usuarioId);
		return gruposToGruposDTO(usuario.getGrupos());
	}
	
	@DeleteMapping("/{grupoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void desassociarFormaPagamento(@PathVariable Long usuarioId, @PathVariable Long grupoId) {
		usuarioService.desassociarFormaPagamento(usuarioId, grupoId);
	}
	
	@PutMapping("/{grupoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void associarFormaPagamento(@PathVariable Long usuarioId, @PathVariable Long grupoId) {
		usuarioService.associarFormaPagamento(usuarioId, grupoId);
	}
	
	private List<GrupoDTO> gruposToGruposDTO(Collection<Grupo> listaPermissoes) {
		return listaPermissoes.stream().map(permissao -> grupoToGrupoDTO(permissao)).collect(Collectors.toList());
	}

	private GrupoDTO grupoToGrupoDTO(Grupo permissao) {
		return modelMapper.map(permissao, GrupoDTO.class);
	}
}
