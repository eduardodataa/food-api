/**
 * 
 */
package com.food.notificacao;

import org.springframework.stereotype.Component;

import com.food.modelo.Cliente;

/**
 * @author duduc
 *
 */
//@Component
public class NotificadorEmail implements Notificador {
	
	private boolean caixaAlta;
	private String hostServidor;
	
	public NotificadorEmail(String hostServidor){
		System.out.println("NotificadorEmail: instância via @Component");
		this.hostServidor = hostServidor;
	}
	
	
	
	@Override
	public void notificar(Cliente cliente, String msg) {
		if(caixaAlta) {
			msg = msg.toUpperCase();
		}
		System.out.printf("Notificando %s através do e-mail %s: %s usando SMTP: %s \n", cliente.getNome(), cliente.getEmail(), msg, hostServidor);
	}

	public boolean isCaixaAlta() {
		return caixaAlta;
	}
	public void setCaixaAlta(boolean caixaAlta) {
		this.caixaAlta = caixaAlta;
	}
	public String getHostServidor() {
		return hostServidor;
	}
	public void setHostServidor(String hostServidor) {
		this.hostServidor = hostServidor;
	}
	
	

}
