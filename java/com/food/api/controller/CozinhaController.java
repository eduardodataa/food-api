package com.food.api.controller;

import java.util.List;
import java.util.Optional;

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
import com.food.domain.model.Cozinha;
import com.food.domain.repository.CozinhaRepository;
import com.food.domain.service.CadastroCozinhaService;

@RestController
@RequestMapping("/cozinhas")
public class CozinhaController {

	@Autowired
	private CozinhaRepository cozinhaRepository;

	@Autowired
	private CadastroCozinhaService cadastroCozinhaService;

	// produces informa o formato que retornará, mesmo a aplicação aceitando outros
	// formatos
//	@GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	@GetMapping
	public List<Cozinha> listar() {
		return cozinhaRepository.findAll();
	}

//	@GetMapping(produces = { MediaType.APPLICATION_XML_VALUE })
//	public CozinhasXmlWrapper listarXML() {
//		return new CozinhasXmlWrapper(cozinhaRepository.listar());
//	}

	@ResponseStatus(HttpStatus.CREATED)
	@GetMapping("/{cozinhaId}")
	public ResponseEntity<Cozinha> buscar(@PathVariable Long cozinhaId) {
		Optional<Cozinha> cozinha = cozinhaRepository.findById(cozinhaId);
		if (cozinha.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		//utilizei isPresent apenas para fins didátivos
		if(cozinha.isPresent()) {
			return ResponseEntity.status(HttpStatus.OK).body(cozinha.get());			
		}
		
		return ResponseEntity.notFound().build();
	}

	@GetMapping("/localInexistente/{cozinhaId}")
	public ResponseEntity<Cozinha> localInexistente(@PathVariable Long cozinhaId) {
		Cozinha cozinha = cozinhaRepository.findById(cozinhaId).get();
		if (cozinha == null) {
			HttpHeaders headers = new HttpHeaders();
			headers.add(HttpHeaders.LOCATION, "http://api.algafood.local:8080/gastronomia/");
			return ResponseEntity.status(HttpStatus.FOUND).headers(headers).build();
		}
		return ResponseEntity.status(HttpStatus.OK).body(cozinha);
	}

	@PostMapping // post não são indempotente, cada post terá efeito colateral que será a persistência
	@ResponseStatus(HttpStatus.CREATED)
	public Cozinha salvar(@RequestBody Cozinha cozinha) {
		System.out.println(cozinha + cozinha.getNome());
		return cadastroCozinhaService.salvar(cozinha);
	}

	@PutMapping("/{cozinhaId}") // atualização de um recurso
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Cozinha> atualizar(@PathVariable Long cozinhaId, @RequestBody Cozinha cozinha) {
		Optional<Cozinha> cozinhaAtual = cozinhaRepository.findById(cozinhaId);
		if(cozinhaAtual.isPresent()) {
			BeanUtils.copyProperties(cozinha, cozinhaAtual.get(), "id");
			return ResponseEntity.ok(cadastroCozinhaService.salvar(cozinhaAtual.get()));
		}
		return ResponseEntity.notFound().build();
	}

	@DeleteMapping("/{cozinhaId}")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<?> excluir(@PathVariable Long cozinhaId) {
		try {
			cadastroCozinhaService.excluir(cozinhaId);
			return ResponseEntity.noContent().build();
		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.notFound().build();
		} catch (EntidadeEmUsoException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
}
