package com.food;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.food.domain.model.Cozinha;
import com.food.domain.service.CadastroCozinhaService;

@ExtendWith(SpringExtension.class)//suporte pra carregar o contexto do spring
@SpringBootTest
class CadastroCozinhaIntegrationTests {

	@Autowired
	private CadastroCozinhaService cadastroCozinhaService;
	
	@Test
	void testarCadastroCozinhaComSucesso() {
		//cenário
		Cozinha novaCozinha = new Cozinha();
		novaCozinha.setNome("Chilena");
		//acao
		
		novaCozinha = cadastroCozinhaService.salvar(novaCozinha);
		
		//validação
		assertThat(novaCozinha).isNotNull();
		assertThat(novaCozinha.getId()).isNotNull();
	}

}
