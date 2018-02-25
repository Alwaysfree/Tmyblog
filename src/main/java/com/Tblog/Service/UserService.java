package com.Tblog.Service;

import com.Tblog.domain.User;

public interface UserService{
	 User getByUsername(String username);
	 User register(User user) throws Exception;
	 User login(String email, String password);
	 void deleteUser(long id) throws Exception;
	 User findByCode(String code);
	 User getByUserId(long id);
	 User updateUser(User user) throws Exception;
}