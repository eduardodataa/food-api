package com.food;

import static org.assertj.core.api.Assertions.assertThat;

import javax.validation.ConstraintViolationException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.food.domain.exception.EntidadeEmUsoException;
import com.food.domain.model.Cozinha;
import com.food.domain.service.CozinhaService;

/**
 * Classe com final IT é de teste de integração. Será executado em outro momento (quando se colocar a palavra 'verify' nos goals do projeto).
 * @author duduc
 *
 */

@ExtendWith(SpringExtension.class)//suporte pra carregar o contexto do spring
@SpringBootTest
class CadastroCozinhaIntegrationTestsIT {

	@Autowired
	private CozinhaService cozinhaService;
	
	@Test
	void testarCadastroCozinhaComSucesso() {
		//cenário
		Cozinha novaCozinha = new Cozinha();
		novaCozinha.setNome("Chilena");
		//acao
		
		novaCozinha = cozinhaService.salvar(novaCozinha);
		
		//validação
		assertThat(novaCozinha).isNotNull();
		assertThat(novaCozinha.getId()).isNotNull();
	}
	
	@Test
	public void deveFalharAoCadastrarCozinhaQuandoSemNome() {

		Cozinha novaCozinha = new Cozinha();
		novaCozinha.setNome("");
		
		ConstraintViolationException exception = 
				Assertions.assertThrows(ConstraintViolationException.class, () ->{
					cozinhaService.salvar(novaCozinha);					
				});
		
		assertThat(exception).isNotNull();
		
	}

	@Test
	public void deve_falhar_cozinha_em_uso() {
		
		EntidadeEmUsoException exception = 
				Assertions.assertThrows(EntidadeEmUsoException.class, () ->{
					cozinhaService.excluir(1l);					
				});
		
		assertThat(exception).isNotNull();
		
	}

	@Test
	public void remover_cozinha() {
		
		EntidadeEmUsoException exception = 
				Assertions.assertThrows(EntidadeEmUsoException.class, () ->{
					cozinhaService.excluir(4l);					
				});
		
		assertThat(exception).isNotNull();
		
	}

}
