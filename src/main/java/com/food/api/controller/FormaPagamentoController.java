package com.food.api.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
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

import com.food.api.model.FormaPagamentoDTO;
import com.food.domain.model.FormaPagamento;
import com.food.domain.repository.FormaPagamentoRepository;
import com.food.domain.service.FormaDePagamentoService;

@RestController
@RequestMapping("/formaPagamento")
public class FormaPagamentoController {

	@Autowired
	private FormaPagamentoRepository formaPagamentoRepository;
	
	@Autowired
	private FormaDePagamentoService formaDePagamentoService;

	@Autowired
	private ModelMapper modelMapper;
	
	@GetMapping
	public List<FormaPagamentoDTO> listar(){
		return formaPagamentosToFormaPagamentoDTO(formaPagamentoRepository.findAll());
	}
	
	@PutMapping("/{formaPagamentoId}")
	@ResponseStatus(HttpStatus.OK)
	public FormaPagamentoDTO atualizar(@PathVariable Long formaPagamentoId, @RequestBody @Valid FormaPagamento formaPagamento) {
		FormaPagamento formaPagamentoAtual = formaDePagamentoService.buscarOuFalhar(formaPagamentoId);
		BeanUtils.copyProperties(formaPagamento,formaPagamentoAtual , "id");
		return formaPagamentoToFormaPagamentoDTO( formaDePagamentoService.salvar(formaPagamentoAtual));
	}
	

	private List<FormaPagamentoDTO> formaPagamentosToFormaPagamentoDTO(List<FormaPagamento> listaFormaPagamento) {
		return listaFormaPagamento.stream().map(estado -> formaPagamentoToFormaPagamentoDTO(estado)).collect(Collectors.toList());
	}

	private FormaPagamentoDTO formaPagamentoToFormaPagamentoDTO(FormaPagamento cidade) {
		return modelMapper.map(cidade, FormaPagamentoDTO.class);
	}

	
	@PostMapping
	public ResponseEntity<?> salvar(@RequestBody FormaPagamento formaPagamento) {
		try {
			formaPagamento = formaDePagamentoService.salvar(formaPagamento);
			return ResponseEntity.status(HttpStatus.CREATED).body(formaPagamento);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body("Erro ao salvar: " + e.getMessage());
		}
	}
	
	@DeleteMapping("/{formaPagamentoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void excluir(@PathVariable Long formaPagamentoId){
		formaDePagamentoService.excluir(formaPagamentoId);
	}
}
