package com.food.api.assembler;

import javax.validation.Valid;

import org.springframework.stereotype.Component;

import com.food.api.model.input.RestauranteInput;
import com.food.domain.model.Cozinha;
import com.food.domain.model.Restaurante;

@Component
public class RestauranteInputDesassembler {


	public Restaurante toDomainModel(@Valid RestauranteInput restauranteInput) {
		Restaurante restaurante = new Restaurante();
//		r.setId(restauranteInput.getId);
		restaurante.setNome(restauranteInput.getNome());
		
		Cozinha c = new Cozinha();
		c.setId(restauranteInput.getCozinha().getId());
		restaurante.setCozinha(c);
		restaurante.setTaxaFrete(restauranteInput.getTaxaFrete());
		return restaurante;
	}

	
}
