package com.food.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.food.domain.model.Estado;
import com.food.domain.repository.EstadoRepository;
import com.food.domain.service.CadastroEstadoService;

@RestController
@RequestMapping("/estados")
public class EstadoController {

	@Autowired
	private EstadoRepository estadoRepository;
	@Autowired
	private CadastroEstadoService cadastroEstadoService;
	
	@GetMapping
	public List<Estado> listar(){
		return estadoRepository.findAll();
	}
	
	//atualizar estado
	@PutMapping("/{estadoId}")
	@ResponseStatus(HttpStatus.OK)
	public Estado atualizar(@PathVariable Long estadoId, @RequestBody @Valid Estado estado) {
		Estado estadoAtual = cadastroEstadoService.buscarOuFalhar(estadoId);
		BeanUtils.copyProperties(estado,estadoAtual , "id");
		return cadastroEstadoService.salvar(estadoAtual);
	}
	
	@PostMapping
	public ResponseEntity<?> salvar(@RequestBody Estado estado) {
		try {
			estado = cadastroEstadoService.salvar(estado);
			return ResponseEntity.status(HttpStatus.CREATED).body(estado);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body("Erro ao salvar: " + e.getMessage());
		}
	}
	
	@DeleteMapping("/{estadoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void excluir(@PathVariable Long estadoId){
		cadastroEstadoService.excluir(estadoId);
	}
}
