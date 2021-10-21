package com.food.api.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
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

import com.food.api.model.CozinhaDTO;
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

	@Autowired
	private ModelMapper modelMapper;
	

	@GetMapping
	public List<CozinhaDTO> listar() {
		return cozinhasToCozinhaDTO(cozinhaRepository.findAll());
	}

	@GetMapping("/{cozinhaId}")
	public CozinhaDTO buscar(@PathVariable Long cozinhaId) {
		return cozinhaToCozinhaDTO(cadastroCozinhaService.buscarOuFalhar(cozinhaId));
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
	public CozinhaDTO salvar(@RequestBody @Valid Cozinha cozinha) {
		System.out.println(cozinha + cozinha.getNome());
		return cozinhaToCozinhaDTO(cadastroCozinhaService.salvar(cozinha));
	}

	@PutMapping("/{cozinhaId}") // atualização de um recurso
	@ResponseStatus(HttpStatus.OK)
	public CozinhaDTO atualizar(@PathVariable Long cozinhaId, @RequestBody @Valid Cozinha cozinha) {
		Cozinha cozinhaAtual = cadastroCozinhaService.buscarOuFalhar(cozinhaId);
		BeanUtils.copyProperties(cozinha, cozinhaAtual, "id");
		return cozinhaToCozinhaDTO(cadastroCozinhaService.salvar(cozinha));
	}

	@DeleteMapping("/{cozinhaId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void excluir(@PathVariable Long cozinhaId) {
		//Apenas o método exlcuir, pois agora a classe de exceção herda do httpstatusexception
		cadastroCozinhaService.excluir(cozinhaId);
		
	}
	
	private List<CozinhaDTO> cozinhasToCozinhaDTO(List<Cozinha> listaCozinhas) {
		return listaCozinhas.stream().map(estado -> cozinhaToCozinhaDTO(estado)).collect(Collectors.toList());
	}

	private CozinhaDTO cozinhaToCozinhaDTO(Cozinha cidade) {
		return modelMapper.map(cidade, CozinhaDTO.class);
	}

}
