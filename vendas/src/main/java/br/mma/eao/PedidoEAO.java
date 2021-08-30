package br.mma.eao;

import org.springframework.data.jpa.repository.JpaRepository;

import br.mma.entities.Pedido;

public interface PedidoEAO extends JpaRepository<Pedido, Integer> {

}
