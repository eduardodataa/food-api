package com.food.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.food.domain.model.Permissao;

public interface PermissaoRepository extends JpaRepository<Permissao, Long>{
	
//	List<Permissao> listar();
//	Permissao buscar(Long id);
//	Permissao salvar(Permissao cozinha);
//	void remover(Permissao cozinha);

}
