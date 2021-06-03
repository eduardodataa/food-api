/**
 * 
 */
package com.food.notificacao;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.food.modelo.Cliente;

/**
 * @author duduc
 *
 */

// @Primary - faz a desambiguação de componentes que implementam a mesma interface que o Spring tenta instanciar na classe ativação
//@Qualifier("email") //solução alternativa para desambiguação
@Profile("prod") //esta annot faz o NotificadorEmail ser registrado somente em produção
@Component
@TipoDoNotificador(NivelUrgencia.SEM_URGENCIA)
public class NotificadorEmail implements Notificador {
	
	public NotificadorEmail(){
		System.out.println("Notificador email real");
	}
	
	@Override
	public void notificar(Cliente cliente, String msg) {
		System.out.printf("Notificando %s através do e-mail %s: %s \n", cliente.getNome(), cliente.getEmail(), msg);
	}

}
