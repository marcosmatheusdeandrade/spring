package br.mma.rest.dto;

import java.math.BigDecimal;
import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class InformacoesPedidoDTO {

	private Integer code;
	private String cpf;
	private String customerName;
	private BigDecimal total;
	private String status;
	
	private List<InformacoesItemPedidoDTO> items;
}
