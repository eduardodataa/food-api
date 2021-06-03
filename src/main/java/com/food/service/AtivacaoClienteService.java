package com.food.service;

import org.springframework.stereotype.Component;

import com.food.modelo.Cliente;
import com.food.notificacao.Notificador;

@Component
public class AtivacaoClienteService {
	
	private Notificador notificador;
	
	public AtivacaoClienteService(Notificador notificador) {
		super();
		this.notificador = notificador;
		System.out.println("AtivacaoClienteService: " + notificador.toString());
	}
	
	
	public void ativar(Cliente cliente) {
		cliente.ativar();
		
		notificador.notificar(cliente, "Seu cadastro no sistema está ativo");
	}



	

}
