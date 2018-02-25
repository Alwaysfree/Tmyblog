package com.Tblog.Controller.Form;

import java.util.Date;

import javax.validation.constraints.Size;

import com.Tblog.domain.Blog;
import com.Tblog.domain.User;


public class BlogForm{
	
	@Size(min=2,max=30)
	private String title;
	
	@Size(min=1)
	private String content;
	
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
		
	public Blog toBlog(User user) {
		Blog blog = new Blog(this.title, this.content, new Date(), user);
		return blog;
	}
}