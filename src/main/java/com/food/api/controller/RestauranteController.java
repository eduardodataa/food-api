package com.food.api.controller;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.server.ServletServerHttpRequest;
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

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.food.api.assembler.RestauranteInputDesassembler;
import com.food.api.assembler.RestauranteModelAssembler;
import com.food.api.model.RestauranteDTO;
import com.food.api.model.input.RestauranteInput;
import com.food.domain.exception.CidadeNaoEncontradaException;
import com.food.domain.exception.CozinhaNaoEncontradaException;
import com.food.domain.exception.EntidadeNaoEncontradaException;
import com.food.domain.exception.NegocioException;
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
	
	@Autowired
	private RestauranteModelAssembler  restauranteModelAssembler;
	
	@Autowired
	private RestauranteInputDesassembler restauranteInputDesassembler ;

	@Autowired
	private ModelMapper modelMapper;
	

	@GetMapping
	public List<RestauranteDTO> listar() {
		List<Restaurante> lista = restauranteRepository.findAll();
		return lista.stream()
				.map(restaurante -> restauranteModelAssembler.toModel(restaurante))
				.collect(Collectors.toList());
	}

	@GetMapping("/{restauranteId}")
	public RestauranteDTO buscar(@PathVariable Long restauranteId) {
		Restaurante restaurante = cadastroRestauranteService.buscarOuFalhar(restauranteId);
		
		RestauranteDTO restauranteDTO = restauranteModelAssembler.toModel(restaurante);
		
		return restauranteDTO;
	}

	@PostMapping // post não são indempotente, cada post terá efeito colateral que será a persistência
	//@Validated(Groups.CozinhaId.class) valida só os atributos que estão anotados com o grupo
	@ResponseStatus(HttpStatus.CREATED)
	public RestauranteDTO salvar(@RequestBody @Valid RestauranteInput restauranteInput) {
		try {
			return restauranteModelAssembler.toModel(cadastroRestauranteService.salvar(restauranteInputDesassembler.toDomainModel(restauranteInput)));
		} catch (CozinhaNaoEncontradaException | CidadeNaoEncontradaException e) {
			throw new NegocioException(e.getMessage());
		}
	}

	/**
	 * {@link RestauranteInput} possui somente os atributos que virão da interface, portanto não corre o risco de apagar dados do objeto original ou de copiar valores nulos
	 * @param restauranteId
	 * @param restauranteInput
	 * @return
	 */
	@PutMapping("/{restauranteId}") // atualização de um recurso
	@ResponseStatus(HttpStatus.OK)
	public RestauranteDTO atualizar(@PathVariable Long restauranteId, @RequestBody @Valid RestauranteInput restauranteInput) {
		Restaurante restauranteAtual = cadastroRestauranteService.buscarOuFalhar(restauranteId);
		
		restauranteInputDesassembler.copyToDomainObject(restauranteInput, restauranteAtual);
		
//		BeanUtils.copyProperties(restaurante, restauranteAtual, "id", "formasPagamento", "endereco", "dataAtualizacao", "dataCadastro", "produtos");
		try {
			return restauranteModelAssembler.toModel(cadastroRestauranteService.salvar(restauranteAtual));
		} catch (EntidadeNaoEncontradaException e) {
			throw new NegocioException(e.getMessage());
		}
	}

	@DeleteMapping("/{restauranteId}") // atualização de um recurso
	@ResponseStatus(HttpStatus.OK)
	public void excluir(@PathVariable Long restauranteId) {
		cadastroRestauranteService.excluir(restauranteId);
	}
	
	@PatchMapping("/{restauranteId}")
	public RestauranteDTO atualizarParcial(@PathVariable Long restauranteId, @RequestBody Map<String, Object> campos, HttpServletRequest httpServletRequest){

		Restaurante restauranteAtual = cadastroRestauranteService.buscarOuFalhar(restauranteId);
		
		merge(campos, restauranteAtual, httpServletRequest);
		
		return atualizar(restauranteId, modelMapper.map(restauranteAtual, RestauranteInput.class));
	}
	
	@PutMapping("/{restauranteId}/ativo")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void ativar(@PathVariable Long restauranteId) {
		cadastroRestauranteService.ativar(restauranteId);
	}
	
	@DeleteMapping("/{restauranteId}/ativo")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void inativar(@PathVariable Long restauranteId) {
		cadastroRestauranteService.inativar(restauranteId);
	}

	private void merge(Map<String, Object> camposOrigem, Restaurante restauranteDestino, HttpServletRequest httpServletRequest) {
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, true);
			objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);

			Restaurante restauranteOrigem = objectMapper.convertValue(camposOrigem, Restaurante.class);
			
			System.out.println(restauranteOrigem);
			
			camposOrigem.forEach((nomePropriedade, valorPropriedade) ->{
				Field field = ReflectionUtils.findField(Restaurante.class, nomePropriedade);
				field.setAccessible(true);
				
				Object novoValor = ReflectionUtils.getField(field, restauranteOrigem);
				
				System.out.println(nomePropriedade + " = " + valorPropriedade + " = " + novoValor);
				
				ReflectionUtils.setField(field, restauranteDestino, novoValor);
				
			});
		} catch (IllegalArgumentException e) {
			Throwable rootCause = ExceptionUtils.getRootCause(e);
			ServletServerHttpRequest servletServerHttpRequest = new ServletServerHttpRequest(httpServletRequest);
			throw new HttpMessageNotReadableException(e.getMessage(), rootCause, servletServerHttpRequest);
		}
	}
	
	
}
