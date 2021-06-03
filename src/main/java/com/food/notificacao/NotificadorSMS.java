/**
 * 
 */
package com.food.notificacao;

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
	
	@Override
	public void notificar(Cliente cliente, String msg) {
		if(caixaAlta) {
			msg = msg.toUpperCase();
		}
		System.out.printf("Notificando %s através do SMS %s: %s \n", cliente.getNome(), cliente.getTelefone(), msg);
	}

	

}
