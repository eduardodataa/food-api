package com.food.api.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.food.api.model.UsuarioDTO;
import com.food.api.model.input.UsuarioInput;
import com.food.api.model.input.UsuarioSenhaInput;
import com.food.api.model.input.SenhaInput;
import com.food.domain.exception.NegocioException;
import com.food.domain.model.Usuario;
import com.food.domain.repository.UsuarioRepository;
import com.food.domain.service.UsuarioService;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	private ModelMapper modelMapper;
	
	@GetMapping
	public List<UsuarioDTO> listar(){
		return usuariosToUsuarioDTO(usuarioRepository.findAll());
	}
	
	//atualizar usuario
	@PutMapping("/{usuarioId}/atualizarDados")
	@ResponseStatus(HttpStatus.CREATED)
	public UsuarioDTO atualizar(@PathVariable Long usuarioId, @RequestBody @Valid UsuarioInput usuario) {
		Usuario usuarioAtual = usuarioService.buscarOuFalhar(usuarioId);
		BeanUtils.copyProperties(usuario,usuarioAtual , "id");
		return usuarioToUsuarioDTO( usuarioService.salvar(usuarioAtual));
	}
	
	//atualizar usuario
	@PutMapping("/{usuarioId}/atualizarSenha")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void atualizar(@PathVariable Long usuarioId, @RequestBody @Valid SenhaInput usuario) {
		usuarioService.atualizarSenha(usuarioId, usuario);
	}
	

	private List<UsuarioDTO> usuariosToUsuarioDTO(List<Usuario> listaUsuarios) {
		return listaUsuarios.stream().map(usuario -> usuarioToUsuarioDTO(usuario)).collect(Collectors.toList());
	}

	private UsuarioDTO usuarioToUsuarioDTO(Usuario usuario) {
		return modelMapper.map(usuario, UsuarioDTO.class);
	}

	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public UsuarioDTO salvar(@RequestBody @Valid UsuarioSenhaInput usuarioInput) {
		try {
			Usuario usuario = modelMapper.map(usuarioInput, Usuario.class);
//			BeanUtils.copyProperties(usuarioInput,usuario);
//			usuario = usuarioService.salvar(usuario);
//			return ResponseEntity.status(HttpStatus.CREATED).body(usuarioToUsuarioDTO(usuario));
			return usuarioToUsuarioDTO(usuarioService.salvar(usuario));
		} catch (Exception e) {
			throw new NegocioException(e.getMessage());
		}
	}
	
	@DeleteMapping("/{usuarioId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void excluir(@PathVariable Long usuarioId){
		usuarioService.excluir(usuarioId);
	}
}
