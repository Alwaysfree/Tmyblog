package com.Tblog.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.Tblog.Dao.es.EsBlogRepository;
import com.Tblog.domain.EsBlog;

@Service
public class EsBlogServiceImpl implements EsBlogService{
	
	@Autowired
	private EsBlogRepository esBlogRepository;
	
	
	/**    
	 * 新增博客
	 * @param: esblog
	 * return  EsBlog   
	 */ 
	@Override
	public EsBlog addBlog(EsBlog esblog) {
		return esBlogRepository.save(esblog);
	}

	/**    
	 * 删除博客
	 * @param: esblog
	 */ 
	@Override
	public void deleteBlog(EsBlog esblog) {
		esBlogRepository.delete(esblog);
	}
	
	/**    
	 * 更新博客
	 * @param: esblog
	 * return  EsBlog   
	 */ 
	@Override
	public EsBlog updateBlog(EsBlog esblog) {
		EsBlog e = esBlogRepository.findOne(esblog.getBlogId());
		e.setId(esblog.getId());
		e.setTitle(esblog.getTitle());
		e.setContent(esblog.getContent());
		e.setCreatedTime(esblog.getCreatedTime());
		e.setUsername(esblog.getUsername());
		return esBlogRepository.save(e);
	}

	
	/**    
	 * 根据关键词查找博客
	 * @param: esblog
	 * return  Page<EsBlog>  
	 */ 
	@Override
	public Page<EsBlog> listsEsBlogs(String keyword, Pageable pageable) {
		return esBlogRepository.findDistinctEsBlogByTitleContainingOrContentContainingOrUsernameContaining(keyword,keyword,keyword, pageable);
	}
	
}