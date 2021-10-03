package com.food.api.assembler;

import org.springframework.stereotype.Component;

import com.food.api.model.CozinhaDTO;
import com.food.api.model.RestauranteDTO;
import com.food.domain.model.Restaurante;

@Component
public class RestauranteModelAssembler {

	public RestauranteDTO toModel(Restaurante restaurante) {
		CozinhaDTO cozinhaDTO = new CozinhaDTO();
		cozinhaDTO.setId(restaurante.getCozinha().getId());
		cozinhaDTO.setNome(restaurante.getCozinha().getNome());
		
		RestauranteDTO restauranteDTO = new RestauranteDTO();
		restauranteDTO.setCozinha(cozinhaDTO);
		restauranteDTO.setId(restaurante.getId());
		restauranteDTO.setNome(restaurante.getNome());
		restauranteDTO.setTaxaFrete(restaurante.getTaxaFrete());
		
		return restauranteDTO;
	}
}
