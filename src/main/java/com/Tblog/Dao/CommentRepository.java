package com.Tblog.Dao;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import com.Tblog.domain.Comment;
@Component
public interface CommentRepository extends JpaRepository<Comment,Long>{
	/**
	 * 通过blog的id获取该博客所有的comment(评论)
	 * 
	 * @param id
	 * @return List<Comment>
	 */
	@Query("select c from Comment c where c.commentBlog.id = ?1")
	List<Comment> findByCommentBlog(long id);
}