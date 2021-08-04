package com.food;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.food.infrastructure.repository.CustomJpaRepositoryImpl;

@SpringBootApplication
@EnableJpaRepositories(repositoryBaseClass = CustomJpaRepositoryImpl.class) //substitui o SimpleJpaRepository pelo CustomJpaRepositoryImpl (que extende dela também)
public class FoodApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(FoodApiApplication.class, args);
	}

}
