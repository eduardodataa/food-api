package com.food.api.controller;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StreamUtils;

import com.food.domain.model.Cozinha;
import com.food.domain.model.Restaurante;
import com.food.domain.repository.CozinhaRepository;
import com.food.domain.repository.RestauranteRepository;
import com.food.util.DatabaseCleaner;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

@ExtendWith(SpringExtension.class)//suporte pra carregar o contexto do spring
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestPropertySource("/application-test.properties") //configura para utilizar o application de test.properties
class RestauranteControllerTest {
	
	/**
	 * @LocalServerPort injeta o número da porta que foi utilizada para levantar o servidor (SpringBootTest.WebEnvironment.RANDOM_PORT)
	 */
	@LocalServerPort
	private int port;

	@Autowired
	private DatabaseCleaner databaseCleaner;
	static final int RESTAURANTE_ID_INEXISTENTE = 700;
	@Autowired
	CozinhaRepository cozinhaRepository;
	@Autowired
	RestauranteRepository restauranteRepository;
	List<Restaurante> listaCozinhas = new ArrayList<>();
	private static final String CORPO_REST_1_SALVAR = getResource("/json/correto/restaurante1.json");
	private static final String CORPO_REST_COZINHA_INEXISTENTE = getResource("/json/incorreto/restaurante1_cozinha_inexistente.json");
	private static final String CORPO_REST_SEM_COZINHA = getResource("/json/incorreto/restaurante_sem_cozinha.json");
	private static final String VIOLACAO_DE_REGRA_DE_NEGOCIO_PROBLEM_TYPE = "Violação de regra de negócio";


	@BeforeEach
	private void setup() {
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
		RestAssured.port = port; //substitui o port(port)
		RestAssured.basePath = "/restaurantes"; //.basePath("/cozinhas")
		databaseCleaner.clearTables();
		prepararDados();
	}

	private void prepararDados() {
		Cozinha cozinha1 = new Cozinha();
		cozinha1.setNome("Tailandesa");
		cozinhaRepository.save(cozinha1);

		
		Restaurante restaurante = new Restaurante();
		restaurante.setNome("Uncle Joe");
		restaurante.setTaxaFrete(new BigDecimal(10));
		restaurante.setCozinha(cozinha1);
		restauranteRepository.save(restaurante);

		cozinha1 = new Cozinha();
		cozinha1.setNome("Brasileira");
		cozinhaRepository.save(cozinha1);
		
	}

	@Test
	void testListar() {
		given()
			.accept(ContentType.JSON)
		.when()
			.get()
		.then()
			.statusCode(HttpStatus.OK.value());
	}

	@Test
	void testSalvarSucesso() {
		given()
			.body(CORPO_REST_1_SALVAR)
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
		.when()
			.post()
		.then()
			.statusCode(HttpStatus.CREATED.value())
			.body("nome", equalTo("Uncle Joe"));
	}

	@Test
	void testBuscar() {
		given()
			.pathParam("restauranteId", 1)
			.accept(ContentType.JSON)
		.when()
			.get("/{restauranteId}")
		.then()
			.statusCode(HttpStatus.OK.value())
			.body("nome", equalTo("Uncle Joe"));
	}
	
	@Test
	void testSalvarRestauranteComCozinhaInexistente() {
		given()
			.body(CORPO_REST_COZINHA_INEXISTENTE)
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
		.when()
			.post()
		.then()
		.statusCode(HttpStatus.BAD_REQUEST.value())
		.body("title", equalTo(VIOLACAO_DE_REGRA_DE_NEGOCIO_PROBLEM_TYPE));
	}
	
	@Test
	void testSalvarRestauranteSemCozinha() {
		given()
			.body(CORPO_REST_SEM_COZINHA)
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
		.when()
			.post()
		.then()
			.statusCode(HttpStatus.BAD_REQUEST.value())
			.body("title", equalTo("Dados Inválidos"));
	}
	
	@Test
	public void deveRetornarStatus404_QuandoConsultarRestauranteInexistente() {
		given()
			.pathParam("cozinhaId", RESTAURANTE_ID_INEXISTENTE)
			.accept(ContentType.JSON)
		.when()
			.get("/{cozinhaId}")
		.then()
			.statusCode(HttpStatus.NOT_FOUND.value());
	}
	

	public static String getResource(String resourceName) {
		try {
			InputStream stream = ResourceUtils.class.getResourceAsStream(resourceName);
			return StreamUtils.copyToString(stream, Charset.forName("UTF-8"));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}
