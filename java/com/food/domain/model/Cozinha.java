/**
 * 
 */
package com.food.domain.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author duduc
 *
 */

@JsonRootName("cozinha") // altera o elemento raiz pro XML
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cozinha {

	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	private Long id;

	@JoinColumn(nullable = false)
	private String nome;

	public Cozinha(String nome) {
		this.nome = nome;
	}
}
