package com.food;

import org.springframework.context.annotation.Configuration;

import com.food.notificacao.Notificador;
import com.food.service.AtivacaoClienteService;

@Configuration
public class ServiceConfig {
	
	/**
	 * Notificador é uma instância gerenciada pelo spring e o spring que resolve e envia
	 * @param notificacao
	 * @return
	 */
	public AtivacaoClienteService ativacaoClienteService(Notificador notificacao) {
		return new AtivacaoClienteService(notificacao);
	}
	
}
