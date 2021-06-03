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


// @Primary - faz a desambiguação de componentes que implementam a mesma interface que o Spring tenta instanciar na classe ativação
//@Qualifier("email") //solução alternativa para desambiguação
@Component
@TipoDoNotificador(NivelUrgencia.SEM_URGENCIA)
public class NotificadorEmail implements Notificador {
	
	private boolean caixaAlta;
	private String hostServidor;
//	
//	public NotificadorEmail(String hostServidor){
//		this.hostServidor = hostServidor;
//	}
//	
	
	
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
