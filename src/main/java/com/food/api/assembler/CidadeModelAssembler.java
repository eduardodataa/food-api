package com.food.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.food.api.model.CozinhaDTO;
import com.food.api.model.RestauranteDTO;
import com.food.domain.model.Restaurante;

@Component
public class CidadeModelAssembler {
	
	@Autowired
	private ModelMapper modelMapper;
	
	public RestauranteDTO toModel(Restaurante restaurante) {
		return modelMapper.map(restaurante, RestauranteDTO.class);
	}

//	public RestauranteDTO toModel2(Restaurante restaurante) {
//		CozinhaDTO cozinhaDTO = new CozinhaDTO();
//		cozinhaDTO.setId(restaurante.getCozinha().getId());
//		cozinhaDTO.setNome(restaurante.getCozinha().getNome());
//		
//		RestauranteDTO restauranteDTO = new RestauranteDTO();
//		restauranteDTO.setCozinha(cozinhaDTO);
//		restauranteDTO.setId(restaurante.getId());
//		restauranteDTO.setNome(restaurante.getNome());
//		restauranteDTO.setTaxaFrete(restaurante.getTaxaFrete());
//		
//		return restauranteDTO;
//	}
}
