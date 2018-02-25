package com.Tblog.Dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

import com.Tblog.domain.Blog;


@Component
public interface BlogRepository extends JpaRepository<Blog, Long> {
	/**
	 * 分页获取所有博客信息
	 * 
	 * @param pageable
	 * @return
	 */
	Page<Blog> findAll(Pageable pageable);
	
	/**
	 * 通过用户id获取个人所有博客分页
	 * 
	 * @param id
	 * @param pageable
	 * @return
	 */
	
	@Query("select b from Blog b where b.creator.id = ?1")
	Page<Blog> findByCreator(long id, Pageable pageable);
	
	/**
	 * 通过tagName获取个人所有博客分页
	 * 
	 * @param tag
	 * @param pageable
	 * @return
	 */
	@Query("select b from Blog b where b.tag.tagName=?1")
    Page<Blog> findByTag(String tagName, Pageable pageable);
	
	
	
	/**
	 * 文章归档信息获取
	 * @return
	 */
	@Query(value="select year(b.createdTime) as year,month(b.createdTime) as month,"
			+ "count(b) as count from Blog b group by year(b.createdTime),month(b.createdTime)",
			countQuery="select count(1) from (select count(1) from blog b group by year(b.createdTime),month(b.createdTime))")
	public List<Object[]> findBlogGroupByTime();
	
	/**
	 * 按年月份获取所有文章信息
	 * @param yearmonth
	 * @return
	 */
	@Query("from Blog b where date_format(b.createdTime,'%Y-%m')=date_format((:yearmonth),'%Y-%m') "
			+ "order by createdleTime desc")
	public List<Blog> findAllblogsByMonth(@Param("yearmonth")Date yearmonth);
	
}