package br.mma.rest.dto;

import java.math.BigDecimal;
import java.util.List;

import javax.validation.constraints.NotNull;

import br.mma.validation.NotEmptyList;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PedidoDTO {

	@NotNull(message = "{field.customer.code.required}")
	private Integer customerId;
	
	private BigDecimal total;
	
	@NotEmptyList
	private List<ItemPedidoDTO> items;
}
