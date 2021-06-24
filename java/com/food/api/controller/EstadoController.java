package com.food.api.controller;

import java.util.List;
import java.util.Optional;

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

import com.food.domain.exception.EntidadeEmUsoException;
import com.food.domain.exception.EntidadeNaoEncontradaException;
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
	public ResponseEntity<?> atualizar(@PathVariable Long estadoId, @RequestBody Estado estado) {
		try {
			Optional<Estado> estadoAtual = estadoRepository.findById(estadoId);
			BeanUtils.copyProperties(estado,estadoAtual , "id");
			return ResponseEntity.ok(cadastroEstadoService.salvar(estadoAtual.get()));
		} catch (Exception e) {
			return ResponseEntity.badRequest().body("Erro na requisição: " + e.getMessage());
		}
		
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
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<?> excluir(@PathVariable Long estadoId){
		try {
			cadastroEstadoService.excluir(estadoId);
			return ResponseEntity.noContent().build();
		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.notFound().build();
		} catch (EntidadeEmUsoException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}
	}
}
