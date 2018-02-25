package com.Tblog.Service;


import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.Tblog.domain.Blog;

public interface BlogService{
	Page<Blog> findAll(Pageable pageable);
	void saveBlog(Blog blog);
	void updateBlog(Blog blog) throws Exception;
	void deleteBlog(long id) throws Exception;
	Page<Blog> findAllBlogsByUserId(long id,Pageable pageable);
	 Blog findById(long id);
	 List<Blog> findBlogByMonth(Date month);
	 List<Object[]> findBlogGroupByTime();
}