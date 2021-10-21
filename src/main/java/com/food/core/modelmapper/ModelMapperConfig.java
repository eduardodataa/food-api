package com.food.core.modelmapper;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

	@Bean
	public ModelMapper modelMapper() {
//		var modelMapper = new ModelMapper();
		
		//mapeamento "de-para"
//		modelMapper.createTypeMap(Restaurante.class, RestauranteDTO.class)
//			.addMapping(Restaurante::getTaxaFrete, RestauranteDTO::setPrecoFrete);
		
		return new ModelMapper();
	}
}
