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

import com.food.api.model.FormaPagamentoDTO;
import com.food.api.model.UsuarioDTO;
import com.food.domain.model.FormaPagamento;
import com.food.domain.model.Restaurante;
import com.food.domain.model.Usuario;
import com.food.domain.service.RestauranteService;

@RestController
@RequestMapping("/restaurantes/{restauranteId}/responsaveis")
public class RestauranteUsuarioController {

	@Autowired
	private RestauranteService restauranteService;

	@Autowired
	private ModelMapper modelMapper;
	
	@GetMapping
	public List<UsuarioDTO> listar(@PathVariable Long restauranteId) {
		
		Restaurante restaurante = restauranteService.buscarOuFalhar(restauranteId);
		return usuariosToUsuariosDTO(restaurante.getUsuario());
		
	}
	
	@DeleteMapping("/{usuarioId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void desassociarFormaPagamento(@PathVariable Long restauranteId, @PathVariable Long usuarioId) {
		restauranteService.desassociarUsuario(restauranteId, usuarioId);
		
	}
	
	@PutMapping("/{usuarioId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void associarFormaPagamento(@PathVariable Long restauranteId, @PathVariable Long usuarioId) {
		restauranteService.sassociarUsuario(restauranteId, usuarioId);
	}
	
	private List<UsuarioDTO> usuariosToUsuariosDTO(Collection<Usuario> listaUsuario) {
		return listaUsuario.stream().map(usuario -> usuarioDTOToUsuarioDTO(usuario)).collect(Collectors.toList());
	}

	private UsuarioDTO usuarioDTOToUsuarioDTO(Usuario usuario) {
		return modelMapper.map(usuario, UsuarioDTO.class);
	}
}
