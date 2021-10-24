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
import com.food.api.model.input.UsuarioInputSenha;
import com.food.domain.exception.NegocioException;
import com.food.domain.model.Usuario;
import com.food.domain.repository.UsuarioRepository;
import com.food.domain.service.CadastroUsuarioService;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private CadastroUsuarioService cadastroUsuarioService;

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
		Usuario usuarioAtual = cadastroUsuarioService.buscarOuFalhar(usuarioId);
		BeanUtils.copyProperties(usuario,usuarioAtual , "id");
		return usuarioToUsuarioDTO( cadastroUsuarioService.salvar(usuarioAtual));
	}
	
	//atualizar usuario
	@PutMapping("/{usuarioId}/atualizarSenha")
	@ResponseStatus(HttpStatus.CREATED)
	public UsuarioDTO atualizar(@PathVariable Long usuarioId, @RequestBody @Valid UsuarioInputSenha usuario) {
		Usuario usuarioAtual = cadastroUsuarioService.atualizarSenha(usuarioId, usuario);
		BeanUtils.copyProperties(usuario,usuarioAtual , "id");
		return usuarioToUsuarioDTO( cadastroUsuarioService.salvar(usuarioAtual));
	}
	

	private List<UsuarioDTO> usuariosToUsuarioDTO(List<Usuario> listaUsuarios) {
		return listaUsuarios.stream().map(usuario -> usuarioToUsuarioDTO(usuario)).collect(Collectors.toList());
	}

	private UsuarioDTO usuarioToUsuarioDTO(Usuario usuario) {
		return modelMapper.map(usuario, UsuarioDTO.class);
	}

	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public UsuarioDTO salvar(@RequestBody @Valid UsuarioInput usuarioInput) {
		try {
			Usuario usuario = modelMapper.map(usuarioInput, Usuario.class);
//			BeanUtils.copyProperties(usuarioInput,usuario);
//			usuario = cadastroUsuarioService.salvar(usuario);
//			return ResponseEntity.status(HttpStatus.CREATED).body(usuarioToUsuarioDTO(usuario));
			return usuarioToUsuarioDTO(cadastroUsuarioService.salvar(usuario));
		} catch (Exception e) {
			throw new NegocioException(e.getMessage());
		}
	}
	
	@DeleteMapping("/{usuarioId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void excluir(@PathVariable Long usuarioId){
		cadastroUsuarioService.excluir(usuarioId);
	}
}