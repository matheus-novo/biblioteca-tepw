package com.biblioteca.api_publica;

import java.sql.Connection;

import javax.sql.DataSource;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
public class ApiPublicaApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiPublicaApplication.class, args);
	}

	@Bean
public CommandLineRunner testConnection(DataSource dataSource) {
    return args -> {
        try (Connection conn = dataSource.getConnection()) {
            System.out.println("✅ CONEXÃO COM SUPABASE ESTABELECIDA!");
        } catch (Exception e) {
            System.err.println("❌ ERRO NA CONEXÃO: " + e.getMessage());
        }
    };
}

}
