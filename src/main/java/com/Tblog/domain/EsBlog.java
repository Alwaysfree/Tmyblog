package com.Tblog.domain;

import java.util.Date;

import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldIndex;

@Document(indexName = "blog", type = "blog") // 文档类
@XmlRootElement
public class EsBlog {
	@Id // 主键
	public long id;

	@Field(index = FieldIndex.not_analyzed) // 不做全文检索字段
	public Long blogId;// 博客id

	public String username;// 用户名

	public String title;// 博客标题

	public String content;// 博客内容

	public String tag;// 博客标签

	@Temporal(value = TemporalType.TIMESTAMP) // 映射为日期+时间
	@Field(index = FieldIndex.not_analyzed) // 不做全文检索字段
	private Date createdTime;// 博客创建时间

	// 构造函数
	public EsBlog() {
	}

	public EsBlog(String title, String content) {
		this.title = title;
		this.content = content;
	}

	public EsBlog(Blog blog) {
		this.blogId = blog.getId();
		this.username = blog.getCreator().getUsername();
		this.title = blog.getTitle();
		this.content = blog.getContent();
		this.createdTime = blog.getCreatedTime();
		this.tag = blog.getTag().getTagName();
	}

	public EsBlog(Long blogId, String username, String title, String content, Date createdTime, String tag) {
		this.blogId = blogId;
		this.username = username;
		this.title = title;
		this.content = content;
		this.createdTime = createdTime;
		this.tag = tag;
	}

	// getter && setter
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Long getBlogId() {
		return blogId;
	}

	public void setBlogId(Long blogId) {
		this.blogId = blogId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

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

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

}