package com.easyexam.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestRestAPIs {

	@GetMapping("/api/test/admin")
	@PreAuthorize("hasRole('ADMIN')")
	public String userAccess() {
		return ">>> Admin Contents";
	}

	@GetMapping("/api/test/student")
	@PreAuthorize("hasRole('STUDENT') or hasRole('TEACHER')")
	public String projectManagementAccess() {
		return ">>> Student Contents";
	}

	@GetMapping("/api/test/teacher")
	@PreAuthorize("hasRole('TEACHER')")
	public String adminAccess() {
		return ">>> Teacher Contents";
	}

}
