package com.food.notificacao;

import com.food.modelo.Cliente;

public interface Notificador {

	void notificar(Cliente cliente, String msg);

}