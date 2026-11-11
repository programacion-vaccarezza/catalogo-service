package com.example.catalogo_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.data.redis.autoconfigure.DataRedisReactiveAutoConfiguration;

// Punto de entrada de la app
@SpringBootApplication(exclude = DataRedisReactiveAutoConfiguration.class)
public class CatalogoServiceApplication {

	// Arranca el contexto de Spring Boot
	public static void main(String[] args) {
		SpringApplication.run(CatalogoServiceApplication.class, args);
	}

}
