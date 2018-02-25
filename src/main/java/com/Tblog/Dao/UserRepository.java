package com.Tblog.Dao;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

import com.Tblog.domain.User;

@Component
public interface UserRepository extends JpaRepository<User,Long>{
	/**
	 * 通过email，password来验证登录信息
	 * 
	 * @param email
	 * @param password
	 * @return user
	 */
	@Query("from User u where u.email=:email and u.password=:password")
	User findByEmailAndPassword(@Param("email")String email,@Param("password")String password);
	
	/**
	 * 通过username找到user
	 * @param password
	 * @return user
	 */
	@Query("select u from User u where u.username = ?1")
	User findByUsername(String username);
	
	/**
	 * 通过email找到user
	 * 
	 * @param email
	 * @return user
	 */
	User findByEmail(String email);
	
	/**
	 * 通过code来验证登录信息
	 * 
	 * @param code
	 * @return user
	 */
	User findByCode(String code);
}
