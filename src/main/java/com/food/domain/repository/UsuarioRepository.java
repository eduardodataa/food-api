package com.food.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.food.domain.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>{
	

}
