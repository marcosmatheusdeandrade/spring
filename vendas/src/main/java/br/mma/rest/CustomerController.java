package br.mma.rest;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import br.mma.eao.CustomerEAOJpaRepository;
import br.mma.entities.Customer;

@Controller
@RequestMapping("/api/customer")
public class CustomerController {

	private CustomerEAOJpaRepository customerEAO;
	
	@ResponseBody
	@PostMapping
	public ResponseEntity save(@RequestBody Customer customer) {
		
		Customer customerDB = customerEAO.save(customer);
		return ResponseEntity.ok(customer);
	}
	
	@ResponseBody
	@GetMapping("/{id}")
	public ResponseEntity findById(@PathVariable("id") Integer id) {
		
		Optional<Customer> optCustomer = customerEAO.findById(id);
		
		if (optCustomer.isPresent()) {
			return ResponseEntity.ok(optCustomer.get());
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@ResponseBody
	@GetMapping
	public ResponseEntity find(Customer customerFilter) {
		
		ExampleMatcher matcher = ExampleMatcher
									.matching()
									.withIgnoreCase()
									.withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
		
		Example<Customer> example = Example.of(customerFilter, matcher);
		
		List<Customer> customers = customerEAO.findAll(example);
		
		return ResponseEntity.ok(customers);
	}
	
	@ResponseBody
	@PutMapping("/{id}")
	public ResponseEntity update(@PathVariable Integer id, @RequestBody Customer customer) {
		
		return customerEAO.findById(id)
						  .map(existingCustomer -> {
			
            customer.setId(existingCustomer.getId());
			customerEAO.save(customer);
			return ResponseEntity.noContent().build();
			
		}).orElseGet(() -> ResponseEntity.notFound().build());
	}
	
	@ResponseBody
	@DeleteMapping("/{id}")
	public ResponseEntity delete(@PathVariable("id") Integer id) {
		
		Optional<Customer> optCustomer = customerEAO.findById(id);
		
		if (optCustomer.isPresent()) {
			customerEAO.delete(optCustomer.get());
			return ResponseEntity.noContent().build();
		}
		
		return ResponseEntity.notFound().build();
	}
}
