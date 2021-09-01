package br.mma.service;

import java.util.Optional;

import br.mma.entities.Pedido;
import br.mma.rest.dto.PedidoDTO;

public interface PedidoService {

	public Pedido save(PedidoDTO pedidoDTO);
	
	public Optional<Pedido> getPedidoCompleto(Integer id);
}
