package br.mma.eao;

import org.springframework.data.jpa.repository.JpaRepository;

import br.mma.entities.ItemPedido;

public interface ItemPedidoEAO extends JpaRepository<ItemPedido, Integer> {

}
