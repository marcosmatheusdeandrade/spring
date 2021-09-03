package br.mma.rest;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.mma.entities.User;
import br.mma.service.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

	private final UserServiceImpl userService;
	private final PasswordEncoder passwordEncoder;
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	private User save(@RequestBody User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		return userService.save(user);
	}
}
