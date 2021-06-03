package com.food;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.food.notificacao.NotificadorEmail;

@Configuration
public class NotificacaoConfig {
	
	@Bean
	public NotificadorEmail notificadorEmail() {
		NotificadorEmail notificadorEmail = new NotificadorEmail("smtp.eduardo.com.br");
		notificadorEmail.setCaixaAlta(true);
		return notificadorEmail;
	}

}
