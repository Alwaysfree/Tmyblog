package com.Tblog.Dao.es;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import com.Tblog.domain.EsBlog;

@Repository
public interface EsBlogRepository extends ElasticsearchRepository<EsBlog,Long>{
	/**
	 * 模糊查询(去重)
	 * @param title
	 * @param content
	 * @param username
	 * @param pageable
	 * @return
	 */
	Page<EsBlog> findDistinctEsBlogByTitleContainingOrContentContainingOrUsernameContaining(String title,String content,String username,Pageable pageable);
}