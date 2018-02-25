package com.Tblog.Controller.Form;


import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;

import com.Tblog.domain.User;

public class UserUpdateForm {
	@Size(min = 5, max = 20)
	private String username;
	
	@Size(min = 0, max = 200)
	private String description;
	
	@Email
	private String email;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public User toUser() {
		User user = new User();
		user.setEmail(this.email);
		user.setUsername(this.username);
		user.setDescription(this.description);
		return user;
	}
}