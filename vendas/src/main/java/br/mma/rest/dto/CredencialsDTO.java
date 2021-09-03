package br.mma.rest.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CredencialsDTO {

	private String login;
	private String password;
}
