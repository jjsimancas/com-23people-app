package com.people.app.controller;

import com.people.app.dto.LoginDto;
import com.people.app.service.auth.AuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

	private final Logger LOGGER = LoggerFactory.getLogger(AuthController.class);

	private final AuthService authService;

	public AuthController(AuthService authService) {
		this.authService = authService;
	}

	@GetMapping("/token")
	@ResponseBody
	public LoginDto authenticateUser() {
		LOGGER.info("[GENERATING TOKEN...]");
		return new LoginDto(authService.tokenGenerator());
	}
}
