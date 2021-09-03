package br.mma.eao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.mma.entities.User;

public interface UserEAO extends JpaRepository<User, Integer>  {

	Optional<User> findByLogin(String login);
}
