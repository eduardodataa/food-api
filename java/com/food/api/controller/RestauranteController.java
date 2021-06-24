package com.food.api.controller;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
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
		return restauranteRepository.findAll();
	}

	@ResponseStatus(HttpStatus.CREATED)
	@GetMapping("/{RestauranteId}")
	public ResponseEntity<Restaurante> buscar(@PathVariable Long RestauranteId) {
		Optional<Restaurante> restaurante = restauranteRepository.findById(RestauranteId);
		if (restaurante == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.status(HttpStatus.OK).body(restaurante.get());
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

	@PutMapping("/{restauranteId}") // atualização de um recurso
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<?> atualizar(@PathVariable Long restauranteId, @RequestBody Restaurante Restaurante) {
		Optional<Restaurante> restauranteAtual;
		try {
			restauranteAtual = restauranteRepository.findById(restauranteId);
			if(restauranteAtual == null) {
				return ResponseEntity.badRequest().body(String.format("Restaurante com id %d não pode ser nulo", restauranteId));
			}
			BeanUtils.copyProperties(Restaurante, restauranteAtual, "id");
			return ResponseEntity.ok(cadastroRestauranteService.salvar(restauranteAtual.get()));
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@DeleteMapping("/{restauranteId}") // atualização de um recurso
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Restaurante> excluir(@PathVariable Long restauranteId) {
		try {
			cadastroRestauranteService.excluir(restauranteId);
			return ResponseEntity.noContent().build();
		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.notFound().build();
		} catch (EntidadeEmUsoException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}
	}
	
	@PatchMapping("/{restauranteId}")
	public ResponseEntity<?> atualizarParcial(@PathVariable Long restauranteId, @RequestBody Map<String, Object> campos){

		Optional<Restaurante> restauranteAtual = restauranteRepository.findById(restauranteId);
		if(restauranteAtual.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		
		merge(campos, restauranteAtual.get());
		
		atualizar(restauranteId, restauranteAtual.get());
		
		return ResponseEntity.ok(restauranteAtual.get());
	}

	private void merge(Map<String, Object> camposOrigem, Restaurante restauranteDestino) {
		ObjectMapper objectMapper = new ObjectMapper();
		Restaurante restauranteOrigem = objectMapper.convertValue(camposOrigem, Restaurante.class);
		
		System.out.println(restauranteOrigem);
		
		camposOrigem.forEach((nomePropriedade, valorPropriedade) ->{
			Field field = ReflectionUtils.findField(Restaurante.class, nomePropriedade);
			field.setAccessible(true);
			
			Object novoValor = ReflectionUtils.getField(field, restauranteOrigem);
			
			System.out.println(nomePropriedade + " = " + valorPropriedade + " = " + novoValor);
			
			ReflectionUtils.setField(field, restauranteDestino, novoValor);
			
		});
		
		
	}
}
