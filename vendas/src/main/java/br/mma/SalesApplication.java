package br.mma;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import br.mma.eao.CustomerEAOJdbcTemplate;
import br.mma.model.Customer;

@SpringBootApplication
@RestController
public class SalesApplication {
	
	@Autowired
	@Qualifier(MyConfiguration.BEAN_APPLICATION_NAME)
	private String applicationName;
	
	@Value("${application.name}")
	private String applicationNameOfProperties;
	
	@Bean
	public CommandLineRunner init(@Autowired CustomerEAOJdbcTemplate eao) {
		return args -> {
			eao.save(new Customer("Cliente teste"));
			eao.save(new Customer("Cliente teste 2"));
			
			List<Customer> customers = eao.findAll();
			customers.forEach(System.out::println);
		};
	}
	
	@GetMapping("/hello")
	public String helloWorld() {
		return applicationName;
	}

	public static void main(String[] args) {
		SpringApplication.run(SalesApplication.class, args);
	}
}
