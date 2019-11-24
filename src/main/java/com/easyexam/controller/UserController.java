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
import java.util.Map;
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
	public ResponseEntity<?> partialUpdate(@RequestBody Map<String, Object> fields) {
		User user = authenticationUtils.getUserObject();

		fields.forEach((k, v) -> {

			if (k.equals("password")) {
				v = encoder.encode(v.toString());
			}
			Field field = ReflectionUtils.findField(User.class, k);
			ReflectionUtils.makeAccessible(field);
			ReflectionUtils.setField(field, user, v);
		});
		userRepository.save(user);

		return ResponseEntity.ok().body("Successfully updated the user.");
	}

}
