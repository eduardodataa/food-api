package com.food.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.food.Groups;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class Restaurante {

	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) //passa a responsabilidade pro BD
	private Long id;

//	@NotNull
//	@NotEmpty
//	@NotNull
	@NotBlank(groups = Groups.CadastroRestaurante.class) //não aceita espaços em branco
	private String nome;

	@NotNull(groups = Groups.CadastroRestaurante.class)
	//@DecimalMin("1")
	@PositiveOrZero
	@Column(nullable = false)
	private BigDecimal taxaFrete;

	@NotNull(groups = Groups.CadastroRestaurante.class)
//	@JsonIgnore
	@Valid // valida as propriedades de cozinha
	@JsonIgnoreProperties({"hibernateLazyInitializer"})
	@ManyToOne(fetch = FetchType.LAZY)//por padrão todo 'ToOne' é eager e o 'ToMany' é lazy
	@JoinColumn(nullable = false)
	private Cozinha cozinha;
	
	@Embedded
	@JsonIgnore
	private Endereco endereco;

	@JsonIgnore
	@CreationTimestamp
	@Column(nullable = false)
	private LocalDateTime dataCadastro;

	@JsonIgnore
	@UpdateTimestamp
	@Column(nullable = false)
	private LocalDateTime dataAtualizacao;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JsonIgnore
	@JoinTable(name = "restaurante_forma_pagamento",
	joinColumns = @JoinColumn(name = "restaurante_id"),
	inverseJoinColumns = @JoinColumn(name = "forma_pagamento_id"))
	private List<FormaPagamento> formasPagamento = new ArrayList<>();

	@JsonIgnore
	@OneToMany(mappedBy = "restaurantes")
	private List<Produto> produtos = new ArrayList<>();
	
	
}
