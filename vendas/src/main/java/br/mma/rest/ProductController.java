package br.mma.rest;

import java.util.List;
import java.util.Optional;

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

import br.mma.eao.ProductEAO;
import br.mma.entities.Product;

@RestController
@RequestMapping("/api/product")
public class ProductController {

	private ProductEAO productEAO;
	
	private static final ResponseStatusException RESPONSE_STATUS_NOT_FOUND = new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado");
	
	public ProductController(ProductEAO productEAO) {
		this.productEAO = productEAO;
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Product save(@RequestBody Product product) {
		return productEAO.save(product);
	}
	
	@GetMapping("/{id}")
	public Product findById(@PathVariable("id") Integer id) {
		
		return productEAO.findById(id)
						  .orElseThrow(() -> RESPONSE_STATUS_NOT_FOUND);
		
	}
	
	@GetMapping
	public List<Product> find(Product productFilter) {
		
		ExampleMatcher matcher = ExampleMatcher
									.matching()
									.withIgnoreCase()
									.withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
		
		Example<Product> example = Example.of(productFilter, matcher);
		
		return productEAO.findAll(example);
	}
	
	@PutMapping("/{id}")
	public Product update(@PathVariable Integer id, @RequestBody Product product) {
		
		return productEAO.findById(id)
						  .map(existingProduct -> {
			
            product.setId(existingProduct.getId());
			productEAO.save(product);
			return existingProduct;
			
		}).orElseThrow(() -> RESPONSE_STATUS_NOT_FOUND);
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable("id") Integer id) {
		
		Optional<Product> optProduct = productEAO.findById(id);
				   
		if (optProduct.isPresent()) {
			productEAO.delete(optProduct.get());
		} else {
			throw RESPONSE_STATUS_NOT_FOUND;
		}
	}
}
