package com.food.domain.repository;

import java.util.Optional;

import com.food.domain.model.Usuario;

public interface UsuarioRepository extends CustomJpaRepository<Usuario, Long>{

	Optional<Usuario> findByEmail(String email);
	

}
