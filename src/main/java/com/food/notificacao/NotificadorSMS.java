/**
 * 
 */
package com.food.notificacao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.food.modelo.Cliente;

import lombok.Getter;
import lombok.Setter;

/**
 * @author duduc
 *
 */
@TipoDoNotificador(NivelUrgencia.URGENTE)
@Component
@Getter
@Setter
public class NotificadorSMS implements Notificador {
	
	private boolean caixaAlta;
	private String hostServidor;

	@Autowired
	private NotificadorProperties notificadorProperties;
	
	@Override
	public void notificar(Cliente cliente, String msg) {
		System.out.println("host :" + notificadorProperties.getHostServidor());
		System.out.println("porta :" + notificadorProperties.getPortaServidor());
		if(caixaAlta) {
			msg = msg.toUpperCase();
		}
		System.out.printf("Notificando %s através do SMS %s: %s \n", cliente.getNome(), cliente.getTelefone(), msg);
	}

	

}
