package com.Tblog.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;


@Entity//实体
@Table//表名为默认 tag
public class Tag{
	@Id //主键
	@GeneratedValue(strategy=GenerationType.AUTO)//自动策略
	private long id;//标识
	
	@Size(min=2, max=10)//2到10个字符
	@Column(nullable = false)//映射为字段，值不能为空
	private String tagName;//标签名
	
	//一对多，被维护
	@OneToMany(mappedBy="tag")
	private List<Blog> blogs = new ArrayList<Blog>();//标签对应的博客
	
	
	public long getId() {
		return id;
	}

	public void setId(long tagId) {
		this.id = tagId;
	}

	public String getTagName() {
		return tagName;
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
	}

	public List<Blog> getBlogs() {
		return blogs;
	}

	public void setBlogs(List<Blog> blogs) {
		this.blogs = blogs;
	}

	public Tag() {
	}
	
	public Tag(String tagName) {
		this.tagName = tagName;
	}

	
	
}