package com.food.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.food.modelo.Cliente;
import com.food.notificacao.Notificador;

@Component
public class AtivacaoClienteService {

	//outra opção de injeção
//	@Autowired "fio-automático"
	private @Autowired Notificador notificador;
	
	//define o construtor padrão
//	@Autowired 
//	public AtivacaoClienteService(Notificador notificador) {
//		this.notificador = notificador;
//	}
//	
//	public AtivacaoClienteService(String notificador) {
//	}
	
	
	public void ativar(Cliente cliente) {
		cliente.ativar();
		
		if(notificador != null) {
			notificador.notificar(cliente, "Seu cadastro no sistema está ativo");
		}else {
			System.out.println("notificador null");
		}
	}

	//outra opção de injeção
//	@Autowired 
//	public void setNotificador(Notificador notificador) {
//		this.notificador = notificador;
//	}


	
	

}
