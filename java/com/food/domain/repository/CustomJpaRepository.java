package com.food.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean //sprint data JPA deve ignorar esta classe e não instaciá-la
public interface CustomJpaRepository<T, ID> extends JpaRepository<T, ID> {

	/**
	 * Esta implamentação servirá para qualquer repositório
	 * @return
	 */
	public Optional<T> buscaPrimeiro();
	
}
