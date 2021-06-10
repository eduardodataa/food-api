/**
 * 
 */
package com.food.notificacao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
@TipoDoNotificador(NivelUrgencia.SEM_URGENCIA)
@Component
public class NotificadorEmail implements Notificador {
	
	@Autowired
	private NotificadorProperties notificadorProperties;
	
	@Value("${notificador.email.porta-servidor}")
	private Integer porta;
	
	
	@Override
	public void notificar(Cliente cliente, String msg) {
		System.out.println("host :" + notificadorProperties.getHostServidor());
		System.out.println("porta :" + notificadorProperties.getPortaServidor());
		System.out.printf("Notificando %s através do e-mail %s: %s \n", cliente.getNome(), cliente.getEmail(), msg);
	}

}
