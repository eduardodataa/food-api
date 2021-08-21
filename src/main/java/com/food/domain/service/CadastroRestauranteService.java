package com.food.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.food.domain.exception.EntidadeEmUsoException;
import com.food.domain.exception.RestauranteNaoEncontradoException;
import com.food.domain.model.Cozinha;
import com.food.domain.model.Restaurante;
import com.food.domain.repository.RestauranteRepository;

@Service
public class CadastroRestauranteService {
	
	private static final String RESTAURANTE_EM_USO = "Restaurante de código %d não pode ser removida";

//	private static final String MSG_RESTAURANTE_NAO_ENCONTRADO = "Não existe um cadastro de Restaurante com código %d";

	@Autowired
	private RestauranteRepository restauranteRepository;
	
	@Autowired
	private CadastroCozinhaService cadastroCozinhaService;
	
	/**
	 * boa prática anotar com @transaction os métodos públicos
	 * @param restaurante
	 * @return
	 */
	@Transactional
	public Restaurante salvar(Restaurante restaurante) {
		try {
			Cozinha cozinha = cadastroCozinhaService.buscarOuFalhar(restaurante.getCozinha().getId());
			restaurante.setCozinha(cozinha);
			return restauranteRepository.save(restaurante);
		} catch (Exception e) {
			throw e;
		}
	}

	@Transactional
	public void excluir(Long restauranteId)  {
		try {
			restauranteRepository.deleteById(restauranteId);
		} catch (EmptyResultDataAccessException e) {
			throw new RestauranteNaoEncontradoException(restauranteId);
		}catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(
					String.format(RESTAURANTE_EM_USO, restauranteId));
		}
	}
	
	public Restaurante buscarOuFalhar(Long restauranteId) {
		return restauranteRepository.findById(restauranteId)
				.orElseThrow( () -> new RestauranteNaoEncontradoException(restauranteId));
	}

}
