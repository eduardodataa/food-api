package com.food.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.food.domain.exception.EntidadeEmUsoException;
import com.food.domain.exception.EntidadeNaoEncontradaException;
import com.food.domain.model.Cozinha;
import com.food.domain.model.Restaurante;
import com.food.domain.repository.CozinhaRepository;
import com.food.domain.repository.RestauranteRepository;

@Service
public class CadastroRestauranteService {
	
	@Autowired
	private RestauranteRepository restauranteRepository;
	
	@Autowired
	private CozinhaRepository cozinhaRepository;
	
	public Restaurante salvar(Restaurante restaurante) throws EntidadeNaoEncontradaException {
		Cozinha cozinha = cozinhaRepository.findById(restaurante.getCozinha().getId())
				.orElseThrow( () -> new EntidadeNaoEncontradaException(
					String.format("Não existe cadastro de cozinha com código %d", restaurante.getCozinha().getId())));
		restaurante.setCozinha(cozinha);
		return restauranteRepository.salvar(restaurante);
	}
	
	public void excluir(Long restauranteId)  {
		try {
			restauranteRepository.remover(restauranteId);
		} catch (EmptyResultDataAccessException e) {
			throw new EntidadeNaoEncontradaException(
					String.format("Não existe um cadastro de Restaurante com código %d", restauranteId));
		}catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(
					String.format("Restaurante de código %d não pode ser removida", restauranteId));
		}
	}

}
