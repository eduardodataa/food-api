package com.food.api.controller;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
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
import com.food.domain.model.Restaurante;
import com.food.domain.repository.RestauranteRepository;
import com.food.domain.service.CadastroRestauranteService;

@RestController
@RequestMapping("/restaurantes")
public class RestauranteController {

	@Autowired
	private RestauranteRepository restauranteRepository;

	@Autowired
	private CadastroRestauranteService cadastroRestauranteService;

	@GetMapping
	public List<Restaurante> listar() {
		return restauranteRepository.listar();
	}

	@ResponseStatus(HttpStatus.CREATED)
	@GetMapping("/{RestauranteId}")
	public ResponseEntity<Restaurante> buscar(@PathVariable Long RestauranteId) {
		Restaurante Restaurante = restauranteRepository.buscar(RestauranteId);
		if (Restaurante == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.status(HttpStatus.OK).body(Restaurante);
	}

	@PostMapping // post não são indempotente, cada post terá efeito colateral que será a
					// persistência
	public ResponseEntity<?> salvar(@RequestBody Restaurante restaurante) {
		try {
			restaurante = cadastroRestauranteService.salvar(restaurante);
			return ResponseEntity.status(HttpStatus.CREATED).body(restaurante);
		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PutMapping("/{RestauranteId}") // atualização de um recurso
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Restaurante> atualizar(@PathVariable Long RestauranteId, @RequestBody Restaurante Restaurante) {
		Restaurante RestauranteAtual = restauranteRepository.buscar(RestauranteId);
		BeanUtils.copyProperties(Restaurante, RestauranteAtual, "id");
		return ResponseEntity.ok(cadastroRestauranteService.salvar(RestauranteAtual));
	}

	@DeleteMapping("/{RestauranteId}") // atualização de um recurso
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Restaurante> excluir(@PathVariable Long RestauranteId) {
		try {
			cadastroRestauranteService.excluir(RestauranteId);
			return ResponseEntity.noContent().build();
		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.notFound().build();
		} catch (EntidadeEmUsoException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}
	}
}
