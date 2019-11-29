package com.easyexam.message.request;

import java.util.Optional;
import java.util.Set;
import javax.validation.constraints.*;

public class UpdateUserForm {

    @Size(min = 1, max = 50, message = "First name length must be between 1 and 50")
	private String firstName;

    @Size(min = 1, max = 50, message = "Last name length must be between 1 and 50")
	private String lastName;

    @Size(max = 50, message = "Email length must be at most 50")
	@Email
	private String email;

    @Size(min = 6, max = 100, message = "Password length must be between 6 and 100")
	private String password;

	private int points;

	public Optional<Integer> getPoints() {
		return Optional.ofNullable(this.points);
	}

	public void setPoints(int points) {
		this.points = points;
	}

	public Optional<String> getFirstName() {
		return Optional.ofNullable(this.firstName);
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public Optional<String> getLastName() {
		return Optional.ofNullable(this.lastName);
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Optional<String> getEmail() {
		return Optional.ofNullable(this.email);
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Optional<String> getPassword() {
		return Optional.ofNullable(this.password);
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
