package br.mma.eao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.mma.entities.Pedido;

public interface PedidoEAO extends JpaRepository<Pedido, Integer> {

	Optional<Pedido> findFetchItemsById(Integer id);
}
