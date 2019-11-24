package com.easyexam.security.utils;

import com.easyexam.model.User;
import com.easyexam.security.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationUtils {

	@Autowired
	UserDetailsServiceImpl userDetailsService;

	public String getAuthenticatedUserEmail() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof UserDetails) {
			return ((UserDetails) principal).getUsername();
		}
		else {
			return principal.toString();
		}
	}

	public User getUserObject() {
		String email = this.getAuthenticatedUserEmail();

		User user = userDetailsService.getUserFromEmail(email);

		return user;
	}

}
