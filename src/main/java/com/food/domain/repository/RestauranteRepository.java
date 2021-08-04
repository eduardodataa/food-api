package com.food.domain.repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.food.domain.model.Cozinha;
import com.food.domain.model.Restaurante;

public interface RestauranteRepository extends CustomJpaRepository<Restaurante, Long>, RestauranteRepositoryQueries{
	
	/**
	 * pode ser usado 'find' 'read' 'get' 'query' e todos tem o mesmo comportamento
	 * @param taxaInicial
	 * @param taxaFinal
	 * @return
	 */
	List<Restaurante> findByTaxaFreteBetween(BigDecimal taxaInicial, BigDecimal taxaFinal);

	/**
	 * Junção de dois filtros distintos
	 * @param nome
	 * @param cozinha
	 * @return
	 */
	List<Cozinha> findByNomeContainingAndCozinhaId(String nome, Long cozinha);	
	
	/**
	 * Filtra o primeiro resultado
	 * @param nome
	 * @return
	 */
	Optional<Restaurante> findFirstRestauranteByNomeContains(String nome);
	
	/**
	 * Retorna os top 2, pode ser 'Asc' ou 'Desc'
	 * @param nome
	 * @return
	 */
	public List<Restaurante> findTop2ByNomeContainingOrderByNomeAsc(String nome);
	
	/**
	 * Retorna o número de restaurantes vinculados a cozinha
	 * @return
	 */
	public int countByCozinhaId(Long cozinhaId);
	
	@Query("from Restaurante where nome like '%:nome%' and cozinha.id = :id")
	List<Restaurante> consultarPorNome(String nome, @Param("id") Long cozinhaId);
	
	
}
