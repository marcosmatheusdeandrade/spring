package br.mma.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.mma.model.Customer;
import br.mma.repository.CustomerRepository;

@Service
public class CustomerService {

	@Autowired
	private CustomerRepository customerRepository;

	public void saveCustomer(Customer customer) {
		validateCustomer(customer);
		this.customerRepository.persist(customer);
	}
	
	public void validateCustomer(Customer customer) {
		
	}
}
