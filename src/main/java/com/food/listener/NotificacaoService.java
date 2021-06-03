package com.food.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.food.notificacao.NivelUrgencia;
import com.food.notificacao.Notificador;
import com.food.notificacao.TipoDoNotificador;
import com.food.service.ClienteAtivadoEvent;

@Component
public class NotificacaoService {
	
	@TipoDoNotificador(NivelUrgencia.SEM_URGENCIA)
	@Autowired
	private Notificador notificador; 
	
	@EventListener
	public void clienteAtivadoListener(ClienteAtivadoEvent ativadoEvent) {
		notificador.notificar(ativadoEvent.getCliente(), "Cliente ativado pelo Listener: ");
	}

}
