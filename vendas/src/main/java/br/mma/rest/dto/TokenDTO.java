package br.mma.rest.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TokenDTO {

	private String login;
	private String token;
	
	public TokenDTO(String login, String token) {
		this.login = login;
		this.token = token;
	}
}
