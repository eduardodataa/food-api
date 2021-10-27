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

import com.food.api.model.CidadeDTO;
import com.food.domain.exception.EntidadeNaoEncontradaException;
import com.food.domain.exception.NegocioException;
import com.food.domain.model.Cidade;
import com.food.domain.repository.CidadeRepository;
import com.food.domain.service.CidadeService;

@RestController
@RequestMapping("/cidades")
public class CidadeController {

	@Autowired
	private CidadeRepository cidadeRepository;

	@Autowired
	private CidadeService cidadeService;

	@Autowired
	private ModelMapper modelMapper;

	@GetMapping
	public List<CidadeDTO> listar() {
		List<Cidade> listaCidade = cidadeRepository.findAll();
		return cidadesToCidadeDTO(listaCidade);
	}

	private List<CidadeDTO> cidadesToCidadeDTO(List<Cidade> listaCidade) {
		return listaCidade.stream().map(cidade -> cidadeToCidadeDTO(cidade)).collect(Collectors.toList());
	}

	private CidadeDTO cidadeToCidadeDTO(Cidade cidade) {
		return modelMapper.map(cidade, CidadeDTO.class);
	}

	@GetMapping("/{cidadeId}")
	public CidadeDTO buscar(@PathVariable Long cidadeId) {
		return cidadeToCidadeDTO(cidadeService.buscarOuFalhar(cidadeId));
	}

	@PostMapping // post não são indempotente, cada post terá efeito colateral que será a
					// persistência
	@ResponseStatus(HttpStatus.CREATED)
	public CidadeDTO salvar(@RequestBody Cidade cidade) {
		try {
			return cidadeToCidadeDTO(cidadeService.salvar(cidade));
		} catch (EntidadeNaoEncontradaException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}

	@PutMapping("/{cidadeId}") // atualização de um recurso
	public CidadeDTO atualizar(@PathVariable Long cidadeId, @RequestBody @Valid Cidade cidade) {
		Cidade cidadeAtual = cidadeService.buscarOuFalhar(cidadeId);
		BeanUtils.copyProperties(cidade, cidadeAtual, "id");
		try {
			return cidadeToCidadeDTO(cidadeService.salvar(cidade));
		} catch (EntidadeNaoEncontradaException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}

	@DeleteMapping("/{cidadeId}") // atualização de um recurso
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void excluir(@PathVariable Long cidadeId) {
		cidadeService.excluir(cidadeId);
	}
	

}
