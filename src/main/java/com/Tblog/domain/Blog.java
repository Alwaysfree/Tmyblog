package com.Tblog.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.data.elasticsearch.annotations.Document;

@Entity // 实体
@Table // 表明数据库的表名，默认blog的表名
@Document(indexName = "blog", type = "blog")
public class Blog {
	@Id // 主键
	@GeneratedValue(strategy = GenerationType.AUTO) // JPA自动选择合适的策略，是默认选项。
	public long id;// 用户的唯一标识

	@Lob // 大对象，映射 MySQL 的 Long Text 类型
	@Basic(fetch=FetchType.LAZY) // 加载时间     懒加载
	public String title;

	@Lob // 大对象，映射 MySQL 的 Long Text 类型
	@Basic(fetch=FetchType.LAZY) // 加载时间     懒加载
	public String content;

	@Temporal(TemporalType.TIMESTAMP) // Date （日期+时间）
	public Date createdTime;

	// 多对一
	// 联级新建，联级更新
	// 不可存在null
	@ManyToOne(cascade = { CascadeType.REFRESH, CascadeType.MERGE }, optional = true)
	@JoinColumn(name = "user_id") // @JoinColumn来定义外键关联的字段名称
	public User creator;//博客对应的作者

	// 多对一
	// 不存在null
	@ManyToOne(optional = true)
	@JoinColumn(name = "tag_id") // @JoinColumn来定义外键关联的字段名称
	private Tag tag;//博客对应的标签

	// 一对多
	// 联级新建，联级删除，联级刷新，联级更新
	// 当前类（Blog）是通过Comment中commentBlog属性与之建立关联的。Blog类为双向关系被维护方。
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "commentBlog")
	private List<Comment> comments = new ArrayList<Comment>();//博客的评论

	// 构造函数
	public Blog() {
	}

	public Blog(String title, String content) {
		super();
		this.title = title;
		this.content = content;
	}

	public Blog(String title, String content, Date createdTime) {
		super();
		this.title = title;
		this.content = content;
		this.createdTime = createdTime;
	}

	public Blog(String title, String content, Date createdTime, User creator) {
		super();
		this.title = title;
		this.content = content;
		this.createdTime = createdTime;
		this.creator = creator;
	}

	public Blog(String title, String content, Date createdTime, User creator, Tag tag) {
		super();
		this.title = title;
		this.content = content;
		this.createdTime = createdTime;
		this.creator = creator;
		this.tag = tag;
	}

	// getter && setter
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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

	public User getCreator() {
		return creator;
	}

	public void setCreator(User creator) {
		this.creator = creator;
	}

	public Tag getTag() {
		return tag;
	}

	public void setTag(Tag tag) {
		this.tag = tag;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

}