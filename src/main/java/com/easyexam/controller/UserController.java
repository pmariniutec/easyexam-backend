package com.easyexam.controller;

import com.easyexam.message.request.UpdateUserForm;
import com.easyexam.model.User;
import com.easyexam.repository.UserRepository;
import com.easyexam.security.utils.AuthenticationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import javax.validation.Valid;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import org.springframework.util.ReflectionUtils;
import java.lang.reflect.Field;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/user/")
public class UserController {

	@Autowired
	AuthenticationUtils authenticationUtils;

	@Autowired
	UserRepository userRepository;

	@Autowired
	PasswordEncoder encoder;

	@GetMapping("")
	public ResponseEntity<?> userInfo() {

		User user = authenticationUtils.getUserObject();

		return ResponseEntity.ok(user);
	}

	@PatchMapping("")
	@PreAuthorize("hasRole('STUDENT') or hasRole('TEACHER')")
	public ResponseEntity<?> partialUpdate(@Valid @RequestBody UpdateUserForm fields) {
		User user = authenticationUtils.getUserObject();

		Optional<String> firstName = fields.getFirstName();
		Optional<String> lastName = fields.getLastName();
		Optional<String> email = fields.getEmail();

		Optional<String> password = fields.getPassword();
		Optional<Integer> points = fields.getPoints();

		if (firstName.isPresent()) {
			user.setFirstName(firstName.get());
		}
		if (lastName.isPresent()) {
			user.setLastName(lastName.get());
		}
		if (email.isPresent()) {
			user.setEmail(email.get());
		}
		if (password.isPresent()) {
			user.setPassword(encoder.encode(password.get()));
		}
		if (points.isPresent()) {
			user.setPoints(points.get());
		}

		userRepository.save(user);

		return ResponseEntity.ok().body("Successfully updated the user.");
	}

}
