package br.mma.model;

import javax.persistence.Entity;

@Entity
public class Customer {

	private Integer id;
	
	private String name;
	
	public Customer() {
	}
	
	public Customer(String name) {
		this.name = name;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
