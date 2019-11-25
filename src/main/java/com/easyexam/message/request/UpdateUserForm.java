package com.easyexam.message.request;

import java.util.Optional;
import java.util.Set;
import javax.validation.constraints.*;

public class UpdateUserForm {

	@Size(min = 3, max = 50)
	private String firstName;

	@Size(min = 3, max = 50)
	private String lastName;

	@Size(max = 60)
	@Email
	private String email;

	@Size(min = 6, max = 40)
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
