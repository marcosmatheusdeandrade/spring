package br.mma.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.OncePerRequestFilter;

import br.mma.security.jwt.JWTService;
import br.mma.security.jwt.JwtAuthFilter;
import br.mma.service.impl.UserServiceImpl;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserServiceImpl userService;
	
	@Autowired
	private JWTService jwtService;
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public OncePerRequestFilter jwtFilter() {
		return new JwtAuthFilter(jwtService, userService);
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		
		auth.userDetailsService(userService).passwordEncoder(passwordEncoder());
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http.csrf().disable()
			.authorizeRequests()
			.antMatchers(HttpMethod.POST, "/api/user/**").permitAll()
			.antMatchers("/api/customer/**").hasAnyRole("USER", "ADMIN")
			.antMatchers("/api/pedido/**").hasAnyRole("USER", "ADMIN")
			.and()
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)// toda request solicita o token
			.and()
			.addFilterBefore(jwtFilter(), UsernamePasswordAuthenticationFilter.class); 
//			.httpBasic();
//			.anyRequest().authenticated() //qualquer outra url não configurada requer autenticação
//			.formLogin()
		
		super.configure(http);
	}
}
