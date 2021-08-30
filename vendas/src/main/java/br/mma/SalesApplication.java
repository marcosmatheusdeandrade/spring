package br.mma;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import br.mma.eao.CustomerEAOJpaRepository;
import br.mma.entities.Customer;

@SpringBootApplication
@RestController
public class SalesApplication {
	
	@Autowired
	@Qualifier(MyConfiguration.BEAN_APPLICATION_NAME)
	private String applicationName;
	
	@Value("${application.name}")
	private String applicationNameOfProperties;
	
	@Bean
	public CommandLineRunner init(@Autowired CustomerEAOJpaRepository eao) {
		return args -> {
			Customer customer = new Customer("Cliente teste");
			eao.save(customer);
			eao.save(new Customer("Cliente teste 2"));
			
			eao.findAll().forEach(System.out::println);
			
			System.out.println("buscando por nome");
			eao.findByNameLike(customer.getName()).forEach(System.out::println);
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
