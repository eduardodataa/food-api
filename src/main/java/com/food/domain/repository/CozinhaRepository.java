package com.food.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.food.domain.model.Cozinha;

@Repository
public interface CozinhaRepository extends CustomJpaRepository<Cozinha, Long>{
	
	/**
	 * find<QUALQUER COISA>byNome funcionará para buscar o campo 'nome'
	 * @param nome
	 * @return
	 */
	List<Cozinha> findQualquerCoisaByNome(String nome);	

	/**
	 * Aqui deve-se garantir que só retornará uma cozinha
	 * A inclusão de Optional é opcional
	 * @param nome
	 * @return
	 */
	Optional<Cozinha> findUmaCozinhaByNome(String nome);

	/**
	 * find<QUALQUER COISA>byNome funcionará para buscar o campo 'nome'
	 * palavra 'Containing'faz a busca parcial '% %'
	 * https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query-methods.query-creation
	 * @param nome
	 * @return
	 */
	List<Cozinha> findQualquerCoisaByNomeContaining(String nome);	

}
