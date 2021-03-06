package com.food.api.controller;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.food.api.model.FormaPagamentoDTO;
import com.food.domain.model.FormaPagamento;
import com.food.domain.model.Restaurante;
import com.food.domain.service.RestauranteService;

@RestController
@RequestMapping("/restaurantes/{restauranteId}/formas-pagamento")
public class RestauranteFormaPagamentoController {

	@Autowired
	private RestauranteService restauranteService;

	@Autowired
	private ModelMapper modelMapper;
	
	@GetMapping
	public List<FormaPagamentoDTO> listar(@PathVariable Long restauranteId) {
		
		Restaurante restaurante = restauranteService.buscarOuFalhar(restauranteId);
		return formasPagamentoToFormasPagamentoDTO(restaurante.getFormasPagamento());
		
	}
	
	@DeleteMapping("/{formaPagamentoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void desassociarFormaPagamento(@PathVariable Long restauranteId, @PathVariable Long formaPagamentoId) {
		restauranteService.desassociarFormaPagamento(restauranteId, formaPagamentoId);
	}
	
	@PutMapping("/{formaPagamentoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void associarFormaPagamento(@PathVariable Long restauranteId, @PathVariable Long formaPagamentoId) {
		restauranteService.associarFormaPagamento(restauranteId, formaPagamentoId);
	}
	
	private List<FormaPagamentoDTO> formasPagamentoToFormasPagamentoDTO(Collection<FormaPagamento> listaFormaPagamento) {
		return listaFormaPagamento.stream().map(formaPagamento -> formaPagamentoToFormaPagamentoDTO(formaPagamento)).collect(Collectors.toList());
	}

	private FormaPagamentoDTO formaPagamentoToFormaPagamentoDTO(FormaPagamento formaPagamento) {
		return modelMapper.map(formaPagamento, FormaPagamentoDTO.class);
	}
}
