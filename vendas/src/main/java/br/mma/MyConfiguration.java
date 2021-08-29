package br.mma;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;

@Development
public class MyConfiguration {

	public static final String BEAN_APPLICATION_NAME = "applicationName";

	@Bean(name=BEAN_APPLICATION_NAME)
	public String applicationName() {
		return "Sistema de Vendas";
	}
	
	@Bean
	public CommandLineRunner run() {
		return args -> {
			System.out.println("Rodando configuração de desenvolvimento ao iniciar servidor");
		};
	}
}
