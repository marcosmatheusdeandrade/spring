package br.mma.security.jwt;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import br.mma.entities.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JWTService {

	@Value("${security.jwt.expiration}")
	private String expiration;
	
	@Value("${security.jwt.signature.key}")
	private String signatureKey;
	
	public String generateToken(User user) {
		
		long expiration = Long.valueOf(this.expiration);
		LocalDateTime dtExpiration = LocalDateTime.now().plusMinutes(expiration);
		Date date = Date.from(dtExpiration.atZone(ZoneId.systemDefault()).toInstant());
		
		return Jwts
				.builder()
				.setSubject(user.getLogin())
				.setExpiration(date)
				.signWith(SignatureAlgorithm.HS512, signatureKey)
				.compact();
	}
	
	public boolean isValidToken(String token) {
		try {
			Claims claims = getClaims(token);
			LocalDateTime dtExpiration = claims.getExpiration()
												.toInstant()
												.atZone(ZoneId.systemDefault()).toLocalDateTime();
		
			return LocalDateTime.now().isBefore(dtExpiration);
		} catch (ExpiredJwtException e) {
			return false;
		}
	}
	
	public String getUserLogin(String token) {
		return getClaims(token).getSubject();
	}
	
	private Claims getClaims(String token) throws ExpiredJwtException {
		return Jwts
				.parser()
				.setSigningKey(signatureKey)
				.parseClaimsJws(token)
				.getBody();
	}
}
