package com.Tblog.Dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import com.Tblog.domain.Blog;
import com.Tblog.domain.Tag;
@Component
public interface TagRepository extends JpaRepository<Tag,Long>{
	/**
	 * 通过tag分页获取所有博客
	 * 
	 * @param tag
	 * @param pageable
	 * @return Page<Blog>
	 */

	Page<Blog> findByTagName(String tag, Pageable pageable);
	
	
	/**
	 * 通过tag分页获取个人博客
	 * 
	 * @param id
	 * @param tag
	 * @param pageable
	 * @return Page<Blog>
	 */

	Page<Blog> findByTagName(long id,String tag, Pageable pageable);
}