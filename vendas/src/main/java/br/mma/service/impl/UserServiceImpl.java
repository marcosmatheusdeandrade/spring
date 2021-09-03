package br.mma.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.mma.eao.UserEAO;

@Service
public class UserServiceImpl implements UserDetailsService {

	@Autowired
	private PasswordEncoder encoder;
	
	@Autowired
	private UserEAO userEAO;
	
	public br.mma.entities.User save(br.mma.entities.User user) {
		return userEAO.save(user);
	}
	
	public UserDetails autenticar(br.mma.entities.User user) {
		UserDetails userDetails = loadUserByUsername(user.getLogin());
		
		boolean correctPassword = encoder.matches(user.getPassword(), userDetails.getPassword());
		
		if (correctPassword) {
			return userDetails;
		}
		
		throw new RuntimeException("Senha inválida");
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		br.mma.entities.User user = userEAO.findByLogin(username)
											.orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));
		
		String [] roles = user.isAdmin() ? 
							new String[] {"ADMIN", "USER"} : 
							new String[] {"USER"};
		
		return User.builder()
				.username(user.getLogin())
				.password(user.getPassword())
				.roles(roles)
				.build();
	}

}
