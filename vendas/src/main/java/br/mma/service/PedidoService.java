package br.mma.service;

import br.mma.entities.Pedido;
import br.mma.rest.dto.PedidoDTO;

public interface PedidoService {

	public Pedido save(PedidoDTO pedidoDTO);
}
