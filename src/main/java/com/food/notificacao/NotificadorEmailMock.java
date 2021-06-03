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
@Profile("dev")
@Component
@TipoDoNotificador(NivelUrgencia.SEM_URGENCIA)
public class NotificadorEmailMock implements Notificador {

	public NotificadorEmailMock(){
		System.out.println("Notificador email mock");
	}
	
	@Override
	public void notificar(Cliente cliente, String msg) {
		System.out.printf("#### MOCK - Notificando %s através do e-mail %s: %s \n", cliente.getNome(), cliente.getEmail(), msg);
	}

}
