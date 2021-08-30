package br.mma.eao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.mma.entities.Customer;

public interface CustomerEAOJpaRepository extends JpaRepository<Customer, Integer> {

	@Modifying
	void deleteByName(String name);

	/**
	 * consulta gerada dinamicamente utilizando convenções
	 */
	List<Customer> findByNameLike(String name);
	
	/**
	 * consulta gerada dinamicamente utilizando annotations
	 */
	@Query(value = "select * from Customer c where c.name like '%:nome%'", nativeQuery = true)
	List<Customer> buscarPorNomeLike(@Param("nome") String name);
	
}
