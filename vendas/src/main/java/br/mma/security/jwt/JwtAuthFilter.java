package br.mma.security.jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import br.mma.service.impl.UserServiceImpl;

public class JwtAuthFilter extends OncePerRequestFilter {

	private JWTService jwtService;
	private UserServiceImpl userService;
	
	public JwtAuthFilter(JWTService jwtService, UserServiceImpl userService) {
		super();
		this.jwtService = jwtService;
		this.userService = userService;
	}
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, 
									HttpServletResponse response, 
									FilterChain filterChain) throws ServletException, IOException {

		String authorization = response.getHeader("Authorization");
		
		if (authorization != null && authorization.startsWith("Bearer")) {
			String token = authorization.split(" ")[1];
			
			if (jwtService.isValidToken(token)) {
				String userLogin = jwtService.getUserLogin(token);
				UserDetails userDetails = userService.loadUserByUsername(userLogin);
				
				UsernamePasswordAuthenticationToken userToken = 
						new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
				
				userToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(userToken);
			}
		}
		
		filterChain.doFilter(request, response);
	}

}
