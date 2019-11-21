package com.easyexam.controller;

import com.easyexam.model.User;
import com.easyexam.security.utils.AuthenticationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/user/")
public class UserController {

  @Autowired AuthenticationUtils authenticationUtils;

  @GetMapping("")
  public ResponseEntity<?> userInfo() {

    User user = authenticationUtils.getUserObject();

    return ResponseEntity.ok(user);
  }
}
