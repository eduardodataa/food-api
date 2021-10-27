package com.food.api.controller;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.food.api.model.ProdutoDTO;
import com.food.domain.exception.ProdutoNaoEncontradoException;
import com.food.domain.model.Produto;
import com.food.domain.model.Restaurante;
import com.food.domain.repository.ProdutoRepository;
import com.food.domain.repository.RestauranteRepository;
import com.food.domain.service.ProdutoService;
import com.food.domain.service.RestauranteService;

@RestController
@RequestMapping("/restaurantes/{restauranteId}/produtos")
public class RestauranteProdutosController {

	@Autowired
	private ProdutoService produtoService;
	
	@Autowired
	private RestauranteService restauranteService;
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@GetMapping
	public List<ProdutoDTO> listar(@PathVariable Long restauranteId) {
		Restaurante restaurante = restauranteService.buscarOuFalhar(restauranteId);
		return produtosToProdutosDTO(restaurante.getProdutos());
	}
	
	@GetMapping("/{produtoId}")
	public ProdutoDTO buscar(@PathVariable Long restauranteId, @PathVariable Long produtoId) {
		Optional<Produto> produtoOptional = produtoRepository.findById(restauranteId, produtoId);
		return produtoToProdutoDTO(produtoOptional.orElseThrow( () -> new ProdutoNaoEncontradoException(produtoId))); //produtosToProdutosDTO(restaurante.getProdutos());
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ProdutoDTO salvar(@PathVariable Long restauranteId, @RequestBody @Valid ProdutoDTO produtoDTO) {
		Restaurante restaurante = restauranteService.buscarOuFalhar(restauranteId);
		Produto produto = produtoDTOToProduto(produtoDTO);
		produto.setRestaurante(restaurante);
		return produtoToProdutoDTO(produtoService.salvar(produto));
	}
//	
//	@GetMapping("/{produtoId}")
//	public List<ProdutoDTO> listar(@PathVariable Long restauranteId, @PathVariable Long produtoId) {
//		return produtosToProdutosDTO(produtoRepository.findByRestaurante(restauranteId));
//		
//	}
	
//	@DeleteMapping("/{formaPagamentoId}")
//	@ResponseStatus(HttpStatus.NO_CONTENT)
//	public void desassociarFormaPagamento(@PathVariable Long restauranteId, @PathVariable Long formaPagamentoId) {
//		restauranteService.desassociarFormaPagamento(restauranteId, formaPagamentoId);
//	}
//	
//	@PutMapping("/{formaPagamentoId}")
//	@ResponseStatus(HttpStatus.NO_CONTENT)
//	public void associarFormaPagamento(@PathVariable Long restauranteId, @PathVariable Long formaPagamentoId) {
//		restauranteService.associarFormaPagamento(restauranteId, formaPagamentoId);
//	}
	
	private List<ProdutoDTO> produtosToProdutosDTO(Collection<Produto> listaProduto) {
		return listaProduto.stream().map(estado -> produtoToProdutoDTO(estado)).collect(Collectors.toList());
	}

	private ProdutoDTO produtoToProdutoDTO(Produto produto) {
		return modelMapper.map(produto, ProdutoDTO.class);
	}
	
	private Produto produtoDTOToProduto(ProdutoDTO produtoDTO) {
		return modelMapper.map(produtoDTO, Produto.class);
	}
}
