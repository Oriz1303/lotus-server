package com.backend.system.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.system.config.JwtProvider;
import com.backend.system.exception.UserException;
import com.backend.system.model.User;
import com.backend.system.model.Verification;
import com.backend.system.repositories.UserRepository;
import com.backend.system.response.AuthResponse;
import com.backend.system.services.CustomUserDetailsService;

@RestController
@RequestMapping("/auth")
public class AuthController {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private JwtProvider jwtProvider;
	@Autowired
	private CustomUserDetailsService userDetailsService;

	@PostMapping("/signup")
	public ResponseEntity<AuthResponse> createUserHandler(@RequestBody User user) throws UserException {
		String email = user.getEmail();
		String password = user.getPassword();
		String fullName = user.getFullName();
		String birthDate = user.getBirthdate();

		User isEmailExist = userRepository.findByEmail(email);
		if (isEmailExist != null) {
			throw new UserException("Email is already used with another account" + email);
		}

		User createUser = new User();
		createUser.setEmail(email);
		createUser.setPassword(passwordEncoder.encode(password));
		createUser.setFullName(fullName);
		createUser.setBirthdate(birthDate);
		createUser.setVerification(new Verification());

		User saveUser = userRepository.save(createUser);
		Authentication authentication = new UsernamePasswordAuthenticationToken(email, password);
		SecurityContextHolder.getContext().setAuthentication(authentication);

		String token = jwtProvider.generateToken(authentication);

		AuthResponse res = new AuthResponse(token, true);

		return new ResponseEntity<AuthResponse>(res, HttpStatus.CREATED);
	}

	@PostMapping("/signin")
	public ResponseEntity<AuthResponse> signin(@RequestBody User user) {
		String username = user.getEmail();
		String password = user.getPassword();

		Authentication authentication = authenticate(username, password);

		String token = jwtProvider.generateToken(authentication);

		AuthResponse res = new AuthResponse(token, true);

		return new ResponseEntity<AuthResponse>(res, HttpStatus.ACCEPTED);
	}

	private Authentication authenticate(String username, String password) {
		UserDetails userDetails = userDetailsService.loadUserByUsername(username);

		if (userDetails == null)
			throw new BadCredentialsException("Invalid  Username! Auth77");

		if (!passwordEncoder.matches(password, userDetails.getPassword()))
			throw new BadCredentialsException("Invalid username or password! Auth80");

		return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
	}
}
