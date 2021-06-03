package com.food;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.food.modelo.Cliente;
import com.food.service.AtivacaoClienteService;

import lombok.AllArgsConstructor;

@Controller
//@AllArgsConstructor
public class MeuPrimeiroController {
	
	private AtivacaoClienteService ativacaoClienteService;
	
	public MeuPrimeiroController(AtivacaoClienteService ativacaoClienteService) {
		this.ativacaoClienteService = ativacaoClienteService;
		
		System.out.println("MeuPrimeiroController: " + ativacaoClienteService);
	}

	@GetMapping("/hello")
	@ResponseBody
	public String hello() {
		Cliente cliente = new Cliente("Edu", "edu@edu.com", "9999", false);
		
		ativacaoClienteService.ativar(cliente);
		
		return "Hello!";
	}
	
}