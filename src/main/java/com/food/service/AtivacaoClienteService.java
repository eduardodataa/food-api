package com.food.service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import com.food.modelo.Cliente;
import com.food.notificacao.NivelUrgencia;
import com.food.notificacao.Notificador;
import com.food.notificacao.TipoDoNotificador;

@Component
public class AtivacaoClienteService {

//	@TipoDoNotificador(NivelUrgencia.SEM_URGENCIA)
//	private @Autowired Notificador notificador;
//	
//	@PostConstruct
//	private void init() {
//		System.out.println("método de inicialização");
//	}
//	
//	@PreDestroy
//	public void destroy() {
//		System.out.println("destruiu");
//	}
	
	@Autowired
	private ApplicationEventPublisher applicationEventPublisher;
	
	public void ativar(Cliente cliente) {
		cliente.ativar();
		
//		notificador.notificar(cliente, "Seu cadastro no sistema está ativo");

		applicationEventPublisher.publishEvent(new ClienteAtivadoEvent(cliente));
		
	}

}
