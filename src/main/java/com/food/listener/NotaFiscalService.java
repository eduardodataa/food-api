package com.food.listener;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.food.service.ClienteAtivadoEvent;

@Component
public class NotaFiscalService {
	
	
	@EventListener
	public void clienteAtivadoListener(ClienteAtivadoEvent ativadoEvent) {
		System.out.println("Emissão da nota fiscal: " + ativadoEvent.getCliente());
	}

}
