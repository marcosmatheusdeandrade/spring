package br.mma.eao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.mma.entities.Customer;

@Repository
public class CustomerEAO {

	@Autowired
	private EntityManager entityManager;
	
	@Transactional
	public Customer save(Customer customer) {
		entityManager.persist(customer);
		return customer;
	}
	
	@Transactional
	public Customer update(Customer customer) {
		entityManager.merge(customer);
		return customer;
	}
	
	@Transactional
	public void delete(Customer customer) {
		entityManager.remove(customer);
	}
	
	@Transactional(readOnly = true)
	public List<Customer> findAll() {
		String jpql = "SELECT FROM Customer c";
		TypedQuery<Customer> typedQuery = entityManager.createQuery(jpql, Customer.class);
		return typedQuery.getResultList();
	}
	
	@Transactional(readOnly = true)
	public List<Customer> findByName(String name) {
		String jpql = "SELECT FROM Customer c WHERE c.name like :name";
		TypedQuery<Customer> typedQuery = entityManager.createQuery(jpql, Customer.class);
		typedQuery.setParameter("name", "%"+name+"%");
		return typedQuery.getResultList();
	}
}
