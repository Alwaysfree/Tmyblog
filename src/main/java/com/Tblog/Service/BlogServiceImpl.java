package com.Tblog.Service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.Tblog.Dao.BlogRepository;
import com.Tblog.domain.Blog;
import com.Tblog.domain.EsBlog;

@Service
public class BlogServiceImpl implements BlogService {
	@Autowired
	private BlogRepository blogRepository;
	@Autowired
	private EsBlogService esBlogService;

	/**
	 * 不含查询条件的分页获取数据
	 *
	 * @param pageable
	 * @return Page<Blog>
	 */
	public Page<Blog> findAll(Pageable pageable) {
		return blogRepository.findAll(pageable);
	}

	/**
	 * 新增博客
	 * 
	 * @param blog
	 */
	public void saveBlog(Blog blog) {
		Blog b = blogRepository.save(blog);// 保存博客
		EsBlog esblog = new EsBlog(b);// 把blog信息封装成esblog
		esBlogService.addBlog(esblog);// spring data elasticsearch 同时保存便于搜索
	}

	/**
	 * 更新博客
	 * 
	 * @param blog
	 * @throws Exception
	 */
	public void updateBlog(Blog blog) throws Exception {
		try {
			Blog post = blogRepository.findOne(blog.getId());// 找到原有blog
			post.setId(blog.getId());// id不变
			post.setTitle(blog.getTitle());// 更新标题
			post.setContent(blog.getContent());// 更新内容
			post.setCreatedTime(blog.getCreatedTime());// 更新时间
			post.setCreator(blog.getCreator());// 保存作者
			post.setTag(blog.getTag());// 更新标签
			blogRepository.saveAndFlush(post);// 保存更新的博客
			esBlogService.updateBlog(new EsBlog(post));
		} catch (Exception e) {
			//
		}
	}

	/**
	 * 删除指定博客
	 * 
	 * @param id
	 * @throws Exception
	 */
	public void deleteBlog(long id) throws Exception {
		try {
			Blog b = blogRepository.findOne(id);
			blogRepository.delete(b);
			esBlogService.deleteBlog(new EsBlog(b));
		} catch (Exception e) {
			//
		}
	}

	/**
	 * 通过用户的id得到用户所有博客分页
	 * 
	 * @param id
	 * @param pageable
	 * @throws Exception
	 */
	public Page<Blog> findAllBlogsByUserId(long id, Pageable pageable) {
		Page<Blog> lists = blogRepository.findByCreator(id, pageable);
		return lists;
	}

	/**
	 * 通过博客的id获得博客
	 * 
	 * @param id
	 * @throws Exception
	 */
	public Blog findById(long id) {
		Blog blog = blogRepository.findOne(id);
		return blog;
	}

	/**
	 * 获取博客文章归档信息
	 */
	public List<Object[]> findBlogGroupByTime() {
		return blogRepository.findBlogGroupByTime();
	}

	/**
	 * 按月份获取文章信息
	 * 
	 * @param month
	 * @return
	 */
	public List<Blog> findBlogByMonth(Date month) {
		return blogRepository.findAllblogsByMonth(month);
	}

}