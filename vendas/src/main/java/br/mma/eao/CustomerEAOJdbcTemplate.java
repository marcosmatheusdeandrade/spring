package br.mma.eao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import br.mma.entities.Customer;

@Repository
public class CustomerEAOJdbcTemplate {

	private static final String  SQL_INSERT = "insert into customer (name) values (?)";
	
	private static final String  SQL_UPDATE = "UPDATE customer set name = ? WHERE ID = ?";

	private static final String  SQL_DELETE = "DELETE FROM customer WHERE ID = ?";

	private static final String SQL_FIND_ALL = "SELECT * FROM CUSTOMER";
	
	private static final String SQL_FIND_BY_NAME = "SELECT * FROM CUSTOMER WHERE NAME LIKE ?";
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public Customer save(Customer customer) {
		jdbcTemplate.update(SQL_INSERT, customer.getName());
		return customer;
	}
	
	public Customer update(Customer customer) {
		jdbcTemplate.update(SQL_UPDATE, customer.getName(), customer.getId());
		return customer;
	}
	
	public void delete(Long id) {
		jdbcTemplate.update(SQL_DELETE, id);
	}
	
	public List<Customer> findAll() {
		return jdbcTemplate.query(SQL_FIND_ALL, getRowMapper());
	}
	
	public List<Customer> findByName(String name) {
		return jdbcTemplate.query(SQL_FIND_BY_NAME, new Object[] {"%"+name+"%"}, getRowMapper());
	}

	private RowMapper<Customer> getRowMapper() {
		return new RowMapper<Customer>() {
			
			@Override
			public Customer mapRow(ResultSet rs, int rowNum) throws SQLException {
				return new Customer(rs.getString("name"));
			}
		};
	}
}
