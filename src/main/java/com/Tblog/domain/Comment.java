package com.Tblog.domain;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity//实体
@Table//默认的表名为comment
public class Comment{
	@Id//主键
	@GeneratedValue(strategy=GenerationType.AUTO)//自动策略
	public long id;//标识
	
	@Lob  // 大对象，映射 MySQL 的 Long Text 类型
	@Basic(fetch=FetchType.LAZY) // 加载时间     懒加载
	public String commentInfo;//评论的内容
	
	@Temporal(TemporalType.TIMESTAMP)
	public Date commentTime;//创建评论的时间
	
	@OneToOne
	//@JoinColumn来定义外键关联的字段名称
	@JoinColumn(name = "user_id")
	public User commentUser;//评论对应的用户
	
	
	//多对一   联级
	 @ManyToOne(cascade = { CascadeType.REFRESH, CascadeType.MERGE }, optional = true)
	 @JoinColumn(name = "blog_id")//外键为blog的id
	public Blog commentBlog;//评论对应的blog
	
	 //getter && setter
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCommentInfo() {
		return commentInfo;
	}
	
	public void setCommentInfo(String commentInfo) {
		this.commentInfo = commentInfo;
	}
	
	public Date getCommentTime() {
		return commentTime;
	}
	
	public void setCommentTime(Date commentTime) {
		this.commentTime = commentTime;
	}
	
	public User getCommentUser() {
		return commentUser;
	}
	
	public void setCommentUser(User commentUser) {
		this.commentUser = commentUser;
	}
	
	public Blog getCommentBlog() {
		return commentBlog;
	}
	
	public void setCommentBlog(Blog commentBlog) {
		this.commentBlog = commentBlog;
	}
	
	
}
