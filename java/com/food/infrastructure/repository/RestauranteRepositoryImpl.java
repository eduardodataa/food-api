package com.food.infrastructure.repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.query.criteria.internal.CriteriaBuilderImpl;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.food.domain.model.Restaurante;
import com.food.domain.repository.RestauranteRepositoryQueries;

@Repository
public class RestauranteRepositoryImpl implements RestauranteRepositoryQueries {

	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public List<Restaurante> consultarPorNomeETaxaInicialEFinal(String nome, BigDecimal taxaFreteInicial, BigDecimal taxaFreteFinal){
		var jpql = new StringBuilder( "from Restaurante where 1=1");
		Map<String, Object> parametros = new HashMap<>();

		if(StringUtils.hasLength(nome)) {
			jpql.append(" and nome like :nome ");
			parametros.put("nome", "%" + nome + "%");
		}
		
		if(taxaFreteInicial != null) {
			jpql.append(" and taxaFrete >= :taxaInicial  ");
			parametros.put("taxaInicial", taxaFreteInicial);
		}
		
		if(taxaFreteFinal != null) {
			jpql.append(" and taxaFrete <= :taxaFinal  ");
			parametros.put("taxaFinal", taxaFreteFinal);
		}
		
		TypedQuery<Restaurante> query = entityManager.createQuery(jpql.toString(), Restaurante.class);
		parametros.forEach((chave, valor) -> query.setParameter(chave, valor));
		return query.getResultList();
		
	}
	
	@Override
	public List<Restaurante> consultarPorNomeETaxaInicialEFinalCriteria(String nome, BigDecimal taxaFreteInicial, BigDecimal taxaFreteFinal){
		
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		
		CriteriaQuery<Restaurante> criteria = builder.createQuery(Restaurante.class);
		
		Root<Restaurante> root = criteria.from(Restaurante.class);
		
		var predicates = new ArrayList<Predicate>();

		if(StringUtils.hasLength(nome)) {
			predicates.add( builder.like(root.get("nome"),  "%" + nome + "%"));
		}

		if(taxaFreteInicial != null) {
			predicates.add(builder.greaterThanOrEqualTo(root.get("taxaFrete"), taxaFreteInicial));
		}
		
		if(taxaFreteFinal != null) {
			predicates.add(builder.lessThanOrEqualTo(root.get("taxaFrete"), taxaFreteFinal));
		}
		
//		Predicate taxaInicialPredicate = builder.greaterThanOrEqualTo(root.get("taxaFrete"), taxaFreteInicial);
//		Predicate taxaFinalPredicate = builder.lessThanOrEqualTo(root.get("taxaFrete"), taxaFreteFinal);
		criteria.where(predicates.toArray(new Predicate[0]));
		
		TypedQuery<Restaurante> query = entityManager.createQuery(criteria);
		
		return query.getResultList();
		
	}
}
