package com.food;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.hasSize;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StreamUtils;

import com.food.domain.model.Cozinha;
import com.food.domain.repository.CozinhaRepository;
import com.food.util.DatabaseCleaner;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

/**
 * Classe com final IT é de teste de integração. Será executado em outro momento (quando se colocar a palavra 'verify' nos goals do projeto).
 * @author duduc
 *
 */

@ExtendWith(SpringExtension.class)//suporte pra carregar o contexto do spring
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT) //levanta o servidor em uma porta aleatória
@TestPropertySource("/application-test.properties") //configura para utilizar o application de test.properties
class CadastroCozinhaTestsIT {
	
	private static final String CORPO_COZINHA_CHINESA = getRecursoCozinha("/json/correto/cozinha.json");


	/**
	 * @LocalServerPort injeta o número da porta que foi utilizada para levantar o servidor (SpringBootTest.WebEnvironment.RANDOM_PORT)
	 */
	@LocalServerPort
	private int port;
	
	@Autowired
	private DatabaseCleaner databaseCleaner;
	
//	@Autowired
//	private Flyway flyway;
	static final int COZINHA_ID_INEXISTENTE = 700;
	
	Cozinha cozinhaAmericana;
	List<Cozinha> listaCozinhas = new ArrayList<>();
	
	@BeforeEach
	public void setUp() {
		//Habilite o registro da solicitação e da resposta se a validação do teste REST Assureds falhar com detalhes de registro
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
		RestAssured.port = port; //substitui o port(port)
		RestAssured.basePath = "/cozinhas"; //.basePath("/cozinhas")

//		//executa o arquivo afterMigrate a cada execução de teste, impedindo que um teste impacte no outro
//		flyway.migrate();
		databaseCleaner.clearTables();
		criarCozinhaAmericana();
		prepararCozinha();
	}

	@Autowired
	private CozinhaRepository cozinhaRepository;
	
	/**
	 * Necessita da parte web da aplicação
	 */
	@Test
	void deveRetornarStatus200_QuandoConsutarCozinhas() {
		given()
			.accept(ContentType.JSON)
		.when()
			.get()
		.then()
			.statusCode(HttpStatus.OK.value());
	}

	@Test
	void deveConter5CozinhasQuandoConsutarCozinhas() {
		given()
			.accept(ContentType.JSON)
		.when()
			.get()
		.then()
		//verificar a propriedade nome
		//Matcher.hasSize serve para escrever expressões. Neste caso procura 5 ocorências do 'nome'
			.body("nome", hasSize(listaCozinhas.size()))
			//Neste caso Matcher.hasItems procura 2 valores do 'nome' : "Brasileira", "Tailandesa"
			.body("nome", hasItems("Tailandesa", "Indiana"));
	}

	@Test
	void deveRetornar501NoCadastroDeCozinha() {
		given()
			.body(CORPO_COZINHA_CHINESA)
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
		.when()
			.post()
		.then()
			.statusCode(HttpStatus.CREATED.value());
	}
	
	@Test
	public void deveRetornarStatusERespostaCorretos_QuandoConsultarCozinhaExistene() {
		given()
			.pathParam("cozinhaId", cozinhaAmericana.getId())
			.accept(ContentType.JSON)
		.when()
			.get("/{cozinhaId}")
		.then()
			.statusCode(HttpStatus.OK.value())
			.body("nome", equalTo(cozinhaAmericana.getNome()));
	}
	
	@Test
	public void deveRetornarStatus404_QuandoConsultarCozinhaInexistene() {
		given()
			.pathParam("cozinhaId", COZINHA_ID_INEXISTENTE)
			.accept(ContentType.JSON)
		.when()
			.get("/{cozinhaId}")
		.then()
			.statusCode(HttpStatus.NOT_FOUND.value());
	}
	
	private void prepararCozinha() {
		
		Cozinha cozinha1 = new Cozinha();
		cozinha1.setNome("Tailandesa");
		
		listaCozinhas.add(cozinha1);
		listaCozinhas.add(cozinhaAmericana);

		cozinha1 = new Cozinha();
		cozinha1.setNome("Brasileira");
		listaCozinhas.add(cozinha1);

		cozinha1 = new Cozinha();
		cozinha1.setNome("Chinesa");
		listaCozinhas.add(cozinha1);

		cozinha1 = new Cozinha();
		cozinha1.setNome("Indiana");
		listaCozinhas.add(cozinha1);
		
		listaCozinhas.stream().forEach( co -> cozinhaRepository.save(co));
		
		
//		cozinhaRepository.save(cozinha1);
//		cozinhaRepository.save(cozinhaAmericana);
//		cozinhaRepository.save(cozinha1);
//		cozinhaRepository.save(cozinha1);
//		cozinhaRepository.save(cozinha1);
	}

	private Cozinha criarCozinhaAmericana() {
		cozinhaAmericana = new Cozinha();
		cozinhaAmericana.setNome("Americana");
		return cozinhaAmericana;
	}
	

	public static String getRecursoCozinha(String resourceName) {
		try {
			InputStream stream = ResourceUtils.class.getResourceAsStream(resourceName);
			return StreamUtils.copyToString(stream, Charset.forName("UTF-8"));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}
