package br.mma.rest.dto;

import java.math.BigDecimal;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class InformacoesItemPedidoDTO {
	
	private String productDescription;
	private BigDecimal price;
	private Integer quantity;
}
