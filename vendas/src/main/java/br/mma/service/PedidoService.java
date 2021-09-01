package br.mma.service;

import java.util.Optional;

import br.mma.entities.Pedido;
import br.mma.enums.StatusPedido;
import br.mma.rest.dto.PedidoDTO;

public interface PedidoService {

	Pedido save(PedidoDTO pedidoDTO);
	
	void updateStatus(Integer id, StatusPedido statusPedido);
	
	Optional<Pedido> getPedidoCompleto(Integer id);
}
