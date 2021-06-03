package com.food;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.food.notificacao.NotificadorEmail;
import com.food.service.AtivacaoClienteService;

/**
 * @Configuration serve para configurar beans
 * @author duduc
 *
 */
//@Configuration
public class Configuracoes {
	
	@Bean
	public NotificadorEmail notificadorEmail() {
		NotificadorEmail notificadorEmail = new NotificadorEmail("smtp.eduardo.com.br");
		notificadorEmail.setCaixaAlta(true);
		return notificadorEmail;
	}
	
	public AtivacaoClienteService ativacaoClienteService() {
		return new AtivacaoClienteService(notificadorEmail());
	}

}
