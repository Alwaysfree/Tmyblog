package com.Tblog.Controller.Form;


import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;

import com.Tblog.domain.User;

public class UserRegisterForm {
	@Size(min = 5, max = 20)
	private String username;
	
	private String password;
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public User toUser() {
		User user = new User();
		user.setEmail(this.email);
		user.setUsername(this.username);
		user.setPassword(this.password);
		return user;
	}
}