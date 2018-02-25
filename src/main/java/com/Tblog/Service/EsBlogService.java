package com.Tblog.Service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.Tblog.domain.EsBlog;

public interface EsBlogService {
	/**
	 * * 创建博客
	 * @param esblog
	 * return EsBlog
	 */
	EsBlog addBlog(EsBlog esblog);

	/**
	 * 刪除博客
	 * @param esblog
	 */
	void deleteBlog(EsBlog esblog);
	
	/**
	 * 更新博客
	 * @param esblog
	 * return EsBlog
	 */
	EsBlog updateBlog(EsBlog esblog);
	
	/**
	 * 根据关键词获取最新博客列表，分页
	 * @param keyword
	 * @param pageable
	 * @return
	 */
	Page<EsBlog> listsEsBlogs(String keyword, Pageable pageable);

}