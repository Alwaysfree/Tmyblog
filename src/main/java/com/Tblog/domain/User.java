package com.Tblog.domain;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

//类为实体类，将映射到指定的数据库表
@Entity
//指明数据库的表名,此处为默认user表名
@Table
public class User{
	@Id //id映射为数据库表的主键
	@GeneratedValue(strategy=GenerationType.AUTO) //自动选择合适的策略
	private long id;//用户唯一标识
	
	private String username;//用户名
	
	private String email;//用户邮箱
	
	private String password;//用户密码
	
	private String description;//用户简介
	
	private String code;//用户激活码
	
	private int status;//用户状态
	
	private String avatar;//用户头像地址
	
	@Temporal(TemporalType.TIMESTAMP)//映射时间为日期+时间
	private Date now;//用户创建时间
	
	//一个用户对应多个blog
	//全部联级，
	//user类为关系被维护方
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "creator")
    private List<Blog> blogs;
 
	//构造函数
	public User() {
	}

	public User(String username, String password) {
		this();
		this.username = username;
		this.password = password;
	}

	public User(String username, String password, String description) {
		this(username,password);
		this.description = description;
	}
	
	

	public User(long id, String username, String email, String password, String description, String avatar, String code,
			int status) {
		this(username,password,description);
		this.code = code;
		this.status = status;
	}

	
	//getter && setter
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	
	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public Date getNow() {
		return now;
	}

	public void setNow(Date now) {
		this.now = now;
	}

	public List<Blog> getBlogs() {
		return blogs;
	}

	public void setBlogs(List<Blog> blogs) {
		this.blogs = blogs;
	}
}