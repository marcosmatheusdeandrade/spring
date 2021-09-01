package br.mma.rest;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.mma.eao.CustomerEAOJpaRepository;
import br.mma.entities.Customer;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {

	private CustomerEAOJpaRepository customerEAO;
	
	private static final ResponseStatusException RESPONSE_STATUS_NOT_FOUND = new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado");
	
	public CustomerController(CustomerEAOJpaRepository customerEAO) {
		this.customerEAO = customerEAO;
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Customer save(@RequestBody @Valid Customer customer) {
		return customerEAO.save(customer);
	}
	
	@GetMapping("/{id}")
	public Customer findById(@PathVariable("id") Integer id) {
		
		return customerEAO.findById(id)
						  .orElseThrow(() -> RESPONSE_STATUS_NOT_FOUND);
		
	}
	
	@GetMapping
	public List<Customer> find(Customer customerFilter) {
		
		ExampleMatcher matcher = ExampleMatcher
									.matching()
									.withIgnoreCase()
									.withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
		
		Example<Customer> example = Example.of(customerFilter, matcher);
		
		return customerEAO.findAll(example);
	}
	
	@PutMapping("/{id}")
	public Customer update(@PathVariable Integer id, @RequestBody Customer customer) {
		
		return customerEAO.findById(id)
						  .map(existingCustomer -> {
			
            customer.setId(existingCustomer.getId());
			customerEAO.save(customer);
			return existingCustomer;
			
		}).orElseThrow(() -> RESPONSE_STATUS_NOT_FOUND);
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable("id") Integer id) {
		
		Optional<Customer> optCustomer = customerEAO.findById(id);
				   
		if (optCustomer.isPresent()) {
			customerEAO.delete(optCustomer.get());
		} else {
			throw RESPONSE_STATUS_NOT_FOUND;
		}
	}
}
