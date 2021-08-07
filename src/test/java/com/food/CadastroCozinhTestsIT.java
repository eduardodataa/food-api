package com.food;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.hasSize;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.food.domain.service.CadastroCozinhaService;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

/**
 * Classe com final IT é de teste de integração. Será executado em outro momento (quando se colocar a palavra 'verify' nos goals do projeto).
 * @author duduc
 *
 */

@ExtendWith(SpringExtension.class)//suporte pra carregar o contexto do spring
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT) //levanta o servidor em uma porta aleatória
class CadastroCozinhTestsIT {
	
	/**
	 * @LocalServerPort injeta o número da porta que foi utilizada para levantar o servidor (SpringBootTest.WebEnvironment.RANDOM_PORT)
	 */
	@LocalServerPort
	private int port;

	@Autowired
	private CadastroCozinhaService cadastroCozinhaService;
	
	/**
	 * Necessita da parte web da aplicação
	 */
	@Test
	void deveRetornarStatus200_QuandoConsutarCozinhas() {
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
		given()
			.basePath("/cozinhas")
			.port(port)
			.accept(ContentType.JSON)
		.when()
			.get()
		.then()
			.statusCode(HttpStatus.OK.value());
	}

	@Test
	void deveConter5CozinhasQuandoConsutarCozinhas() {
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
		given()
			.basePath("/cozinhas")
			.port(port)
			.accept(ContentType.JSON)
		.when()
			.get()
		.then()
		//verificar a propriedade nome
		//Matcher.hasSize serve para escrever expressões. Neste caso procura 5 ocorências do 'nome'
			.body("nome", hasSize(5))
			//Neste caso Matcher.hasItems procura 2 valores do 'nome' : "Brasileira", "Tailandesa"
			.body("nome", hasItems("Tailandesa", "Indiana"));
	}

}
