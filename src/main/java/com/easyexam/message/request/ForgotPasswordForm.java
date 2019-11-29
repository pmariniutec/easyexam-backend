package com.easyexam.message.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class ForgotPasswordForm {
	@NotBlank
	@Size(min = 3, max = 60)
	private String email;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
