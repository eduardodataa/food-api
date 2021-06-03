package com.food.notificacao;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import org.springframework.beans.factory.annotation.Qualifier;

@Retention(RetentionPolicy.RUNTIME) //informa quanto tempo deve permanece onde ela foi usada. Em tempo de execução
@Qualifier
public @interface TipoDoNotificador {
	
	NivelUrgencia value();

}
