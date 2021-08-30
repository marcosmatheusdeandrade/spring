package br.mma.eao;

import org.springframework.data.jpa.repository.JpaRepository;

import br.mma.entities.Product;

public interface ProductEAO extends JpaRepository<Product, Integer> {

}
