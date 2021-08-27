package br.mma;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyConfiguration {

	public static final String BEAN_APPLICATION_NAME = "applicationName";

	@Bean(name=BEAN_APPLICATION_NAME)
	public String applicationName() {
		return "Sistema de Vendas";
	}
}
