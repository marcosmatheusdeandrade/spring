package br.mma;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class SalesApplication {
	
	@Autowired
	@Qualifier(MyConfiguration.BEAN_APPLICATION_NAME)
	private String applicationName;
	
	@GetMapping("/hello")
	public String helloWorld() {
		return applicationName;
	}

	public static void main(String[] args) {
		SpringApplication.run(SalesApplication.class, args);
	}
}
