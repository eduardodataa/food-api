package com.food.core.jackson;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.module.SimpleModule;
import com.food.api.model.CidadeMixin;
import com.food.api.model.CozinhaMixin;
import com.food.api.model.RestauranteMixin;
import com.food.domain.model.Cidade;
import com.food.domain.model.Cozinha;
import com.food.domain.model.Restaurante;

@Component
public class JacksonMixinModule extends SimpleModule {

	private static final long serialVersionUID = 1L;

	public JacksonMixinModule() {
		setMixInAnnotation(Restaurante.class, RestauranteMixin.class);
		setMixInAnnotation(Cidade.class, CidadeMixin.class);
		setMixInAnnotation(Cozinha.class, CozinhaMixin.class);
	}
}
