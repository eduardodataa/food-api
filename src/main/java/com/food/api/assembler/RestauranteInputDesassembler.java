package com.food.api.assembler;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.food.api.model.input.RestauranteInput;
import com.food.domain.model.Cidade;
import com.food.domain.model.Cozinha;
import com.food.domain.model.Restaurante;

@Component
public class RestauranteInputDesassembler {

	@Autowired
	private ModelMapper modelMapper;
	

	public Restaurante toDomainModel(@Valid RestauranteInput restauranteInput) {
		return modelMapper.map(restauranteInput, Restaurante.class);
	}

	public Restaurante toDomainModel2(@Valid RestauranteInput restauranteInput) {
		Restaurante restaurante = new Restaurante();
//		r.setId(restauranteInput.getId);
		restaurante.setNome(restauranteInput.getNome());
		
		Cozinha c = new Cozinha();
		c.setId(restauranteInput.getCozinha().getId());
		restaurante.setCozinha(c);
		restaurante.setTaxaFrete(restauranteInput.getTaxaFrete());
		return restaurante;
	}
	
	/**
	 * Restaurante é objeto de destino
	 * @param restauranteInput
	 * @param restaurante
	 */
	public void copyToDomainObject(RestauranteInput restauranteInput, Restaurante restaurante) {
		//para evitar exception
		restaurante.setCozinha(new Cozinha());
		
		if(restaurante.getEndereco() != null) {
			restaurante.getEndereco().setCidade(new Cidade());
		}
		
		modelMapper.map(restauranteInput, restaurante);
	}

	
}
