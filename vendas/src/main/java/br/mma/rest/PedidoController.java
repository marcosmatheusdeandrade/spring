package br.mma.rest;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.mma.entities.ItemPedido;
import br.mma.entities.Pedido;
import br.mma.rest.dto.InformacoesItemPedidoDTO;
import br.mma.rest.dto.InformacoesPedidoDTO;
import br.mma.rest.dto.PedidoDTO;
import br.mma.service.PedidoService;

@RestController
@RequestMapping("/api/pedido")
public class PedidoController {

	private PedidoService pedidoService;
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Integer save(@RequestBody PedidoDTO pedidoDTO) {
		Pedido pedido = pedidoService.save(pedidoDTO);
		return pedido.getId();
	}
	
	@GetMapping
	public InformacoesPedidoDTO getPedidoCompleto(@PathVariable Integer id) {
		return pedidoService.getPedidoCompleto(id)
							.map(this::converter)
							.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND ,"Pedido não encontrado"));
	}

	private InformacoesPedidoDTO converter(Pedido pedido) {
		return InformacoesPedidoDTO
					.builder()
					.code(pedido.getId())
					.total(pedido.getTotal())
					.customerName(pedido.getCustomer().getName())
					.items(converterItems(pedido.getItems()))
					.build();
	}

	private List<InformacoesItemPedidoDTO> converterItems(List<ItemPedido> items) {
		
		return items.stream()
					.map(item -> {
			
			return InformacoesItemPedidoDTO
						.builder()
						.price(item.getProduct().getPrice())
						.productDescription(item.getProduct().getDescription())
						.quantity(item.getQuantity())
						.build();
			
		}).collect(Collectors.toList());
		
	}
}
