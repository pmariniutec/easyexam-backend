package com.easyexam.controller;

import java.util.HashSet;
import java.util.Set;

import javax.validation.Valid;

import com.easyexam.message.request.LoginForm;
import com.easyexam.message.request.RegisterForm;
import com.easyexam.message.response.JwtResponse;
import com.easyexam.model.Role;
import com.easyexam.model.RoleName;
import com.easyexam.model.User;
import com.easyexam.repository.RoleRepository;
import com.easyexam.repository.UserRepository;
import com.easyexam.security.jwt.JwtToken;
import com.easyexam.security.service.UserDetailsServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthRestAPIs {

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	private JwtToken jwtToken;

	@Autowired
	private UserDetailsServiceImpl userDetailsService;

	@PostMapping("/login")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginForm loginRequest) {

		authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

		final UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getEmail());

		final String token = jwtToken.generateToken(userDetails);

		return ResponseEntity.ok(new JwtResponse(token));
	}

	@PostMapping("/register")
	public ResponseEntity<String> registerUser(@Valid @RequestBody RegisterForm registerRequest) {

		if (userRepository.existsByEmail(registerRequest.getEmail())) {
            return new ResponseEntity<String>("Email is already in use", HttpStatus.BAD_REQUEST);
		}

		// Creating user account
		User user = new User(registerRequest.getFirstName(), registerRequest.getLastName(), registerRequest.getEmail(),
				encoder.encode(registerRequest.getPassword()));

		Set<String> strRoles = registerRequest.getRole();
		Set<Role> roles = new HashSet<Role>();

		strRoles.forEach(role -> {
			switch (role) {
			case "ROLE_ADMIN":
				Role adminRole = roleRepository.findByName(RoleName.ROLE_ADMIN)
						.orElseThrow(() -> new RuntimeException("Error: User Role not found"));
				roles.add(adminRole);

				break;
			case "ROLE_STUDENT":
				Role studentRole = roleRepository.findByName(RoleName.ROLE_STUDENT)
						.orElseThrow(() -> new RuntimeException("Error: User Role not found"));
				roles.add(studentRole);

				break;
			case "ROLE_TEACHER":
				Role teacherRole = roleRepository.findByName(RoleName.ROLE_TEACHER)
						.orElseThrow(() -> new RuntimeException("Error: User Role not found"));
				roles.add(teacherRole);

				break;
			}
		});

		user.setRoles(roles);
		userRepository.save(user);

		return ResponseEntity.ok().body("User registered successfully!");
	}

}
