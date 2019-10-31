package com.easyexam.auth.controller;

import com.easyexam.jwt.JwtUtils;
import com.easyexam.message.request.LoginForm;
import com.easyexam.message.request.RegisterForm;
import com.easyexam.message.response.JwtResponse;
import com.easyexam.model.Role;
import com.easyexam.model.RoleName;
import com.easyexam.model.User;
import com.easyexam.repository.RoleRepository;
import com.easyexam.repository.UserRepository;
import java.util.HashSet;
import java.util.Set;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

  @Autowired AuthenticationManager authenticationManager;

  @Autowired UserRepository userRepository;

  @Autowired RoleRepository roleRepository;

  @Autowired PasswordEncoder encoder;

  @Autowired JwtUtils jwtUtils;

  @PostMapping("/login")
  public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginForm loginRequest) {

    Authentication authentication =
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                loginRequest.getEmail(), loginRequest.getPassword()));

    SecurityContextHolder.getContext().setAuthentication(authentication);

    String jwt = jwtUtils.generateJwtToken(authentication);
    return ResponseEntity.ok(new JwtResponse(jwt));
  }

  @PostMapping("/register")
  public ResponseEntity<String> registerUser(@Valid @RequestBody RegisterForm registerRequest) {

    if (userRepository.existsByEmail(registerRequest.getEmail())) {
      return new ResponseEntity<String>("Fail -> Email is already in use!", HttpStatus.BAD_REQUEST);
    }

    // Creating user account
    User user =
        new User(
            registerRequest.getName(),
            registerRequest.getEmail(),
            encoder.encode(registerRequest.getPassword()));

    Set<String> strRoles = registerRequest.getRole();
    Set<Role> roles = new HashSet<>();

    strRoles.forEach(
        role -> {
          switch (role) {
            case "admin":
              Role adminRole =
                  roleRepository
                      .findByName(RoleName.ROLE_ADMIN)
                      .orElseThrow(
                          () -> new RuntimeException("Fail! -> Cause: User Role not find."));
              roles.add(adminRole);

              break;
            case "student":
              Role studentRole =
                  roleRepository
                      .findByName(RoleName.ROLE_STUDENT)
                      .orElseThrow(
                          () -> new RuntimeException("Fail! -> Cause: User Role not find."));
              roles.add(studentRole);

              break;
            case "teacher":
              Role teacherRole =
                  roleRepository
                      .findByName(RoleName.ROLE_TEACHER)
                      .orElseThrow(
                          () -> new RuntimeException("Fail! -> Cause: User Role not find."));
              roles.add(teacherRole);

              break;
          }
        });

    user.setRoles(roles);
    userRepository.save(user);

    return ResponseEntity.ok().body("User registered successfully!");
  }
}
