package br.mma.service.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.mma.eao.CustomerEAOJpaRepository;
import br.mma.eao.ItemPedidoEAO;
import br.mma.eao.PedidoEAO;
import br.mma.eao.ProductEAO;
import br.mma.entities.Customer;
import br.mma.entities.ItemPedido;
import br.mma.entities.Pedido;
import br.mma.entities.Product;
import br.mma.enums.StatusPedido;
import br.mma.exception.PedidoNotFoundException;
import br.mma.exception.SalesException;
import br.mma.rest.dto.ItemPedidoDTO;
import br.mma.rest.dto.PedidoDTO;
import br.mma.service.PedidoService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PedidoServiceImpl implements PedidoService {

	private final PedidoEAO pedidoEAO;
	private final CustomerEAOJpaRepository customerEAO;
	private final ProductEAO productEAO;
	private final ItemPedidoEAO itemPedidoEAO;
	
	@Override
	@Transactional
	public Pedido save(PedidoDTO pedidoDTO) {
		
		Integer customerId = pedidoDTO.getCustomerId();
		
		Customer customer = customerEAO
							  .findById(customerId)
							  .orElseThrow(() -> new SalesException("Código de cliente inválido"));
		
		Pedido pedido = new Pedido();
		pedido.setTotal(pedidoDTO.getTotal());
		pedido.setDataPedido(LocalDate.now());
		pedido.setCustomer(customer);
		pedido.setStatus(StatusPedido.REALIZADO);
		
		List<ItemPedido> itemsPedido = converterItems(pedido, pedidoDTO.getItems());
		
		pedidoEAO.save(pedido);
		itemPedidoEAO.saveAll(itemsPedido);
		
		return pedido;
	}

	@Override
	@Transactional
	public void updateStatus(Integer id, StatusPedido statusPedido) {

		pedidoEAO.findById(id)
				 .map(pedido -> {
			
			pedido.setStatus(statusPedido);
			return pedidoEAO.save(pedido);
			
		}).orElseThrow(() -> new PedidoNotFoundException());
	}
	
	@Override
	public Optional<Pedido> getPedidoCompleto(Integer id) {
		return pedidoEAO.findFetchItemsById(id);
	}
	
	private List<ItemPedido> converterItems(Pedido pedido, List<ItemPedidoDTO> items) {
		
		if (items.isEmpty()) {
			throw new RuntimeException("Não é possível realizar um pedido sem items.");
		}
		
		return items.stream().map(dto -> {
			
			Product product = productEAO
								.findById(dto.getProductId())
								.orElseThrow(() -> new SalesException("Id de Produto inválido"));
			
			ItemPedido itemPedido = new ItemPedido();
			itemPedido.setQuantity(dto.getQuantity());
			itemPedido.setPedido(pedido);
			itemPedido.setProduct(product);
			
			return itemPedido;
		
		}).collect(Collectors.toList());
	}
}
