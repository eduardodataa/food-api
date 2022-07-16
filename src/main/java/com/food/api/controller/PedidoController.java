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

import com.food.api.model.PedidoDTO;
import com.food.domain.model.Pedido;
import com.food.domain.repository.PedidoRepository;
import com.food.domain.service.PedidoService;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

	@Autowired
	private PedidoRepository pedidoRepository;

	@Autowired
	private PedidoService pedidoService;

	@Autowired
	private ModelMapper modelMapper;
	

	@GetMapping
	public List<PedidoDTO> listar() {
		return pedidosToPedidoDTO(pedidoRepository.findAll());
	}

	@GetMapping("/{pedidoId}")
	public PedidoDTO buscar(@PathVariable Long pedidoId) {
		return pedidoToPedidoDTO(pedidoService.buscarOuFalhar(pedidoId));
	}

	@GetMapping("/localInexistente/{pedidoId}")
	public ResponseEntity<Pedido> localInexistente(@PathVariable Long pedidoId) {
		Pedido Pedido = pedidoRepository.findById(pedidoId).get();
		if (Pedido == null) {
			HttpHeaders headers = new HttpHeaders();
			headers.add(HttpHeaders.LOCATION, "http://api.algafood.local:8080/gastronomia/");
			return ResponseEntity.status(HttpStatus.FOUND).headers(headers).build();
		}
		return ResponseEntity.status(HttpStatus.OK).body(Pedido);
	}

	@PostMapping // post não são indempotente, cada post terá efeito colateral que será a persistência
	@ResponseStatus(HttpStatus.CREATED)
	public PedidoDTO salvar(@RequestBody @Valid Pedido pedido) {
		return pedidoToPedidoDTO(pedidoService.salvar(pedido));
	}

	@PutMapping("/{pedidoId}") // atualização de um recurso
	@ResponseStatus(HttpStatus.OK)
	public PedidoDTO atualizar(@PathVariable Long pedidoId, @RequestBody @Valid Pedido pedido) {
		Pedido PedidoAtual = pedidoService.buscarOuFalhar(pedidoId);
		BeanUtils.copyProperties(pedido, PedidoAtual, "id");
		return pedidoToPedidoDTO(pedidoService.salvar(pedido));
	}

	@DeleteMapping("/{pedidoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void excluir(@PathVariable Long pedidoId) {
		//Apenas o método exlcuir, pois agora a classe de exceção herda do httpstatusexception
		pedidoService.excluir(pedidoId);
	}
	
	private List<PedidoDTO> pedidosToPedidoDTO(List<Pedido> listaPedidos) {
		return listaPedidos.stream().map(pedido -> pedidoToPedidoDTO(pedido)).collect(Collectors.toList());
	}

	private PedidoDTO pedidoToPedidoDTO(Pedido pedido) {
		return modelMapper.map(pedido, PedidoDTO.class);
	}

}
