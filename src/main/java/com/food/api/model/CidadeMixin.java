package com.food.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.food.domain.model.Estado;

public class CidadeMixin {

	@JsonIgnoreProperties(value = "nome", allowGetters = true)
	private Estado estado;

}
