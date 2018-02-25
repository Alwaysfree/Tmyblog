package com.Tblog.Controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.Tblog.Controller.Form.BlogForm;
import com.Tblog.Dao.BlogRepository;
import com.Tblog.Dao.CommentRepository;
import com.Tblog.Dao.TagRepository;
import com.Tblog.domain.Blog;
import com.Tblog.domain.Comment;
import com.Tblog.domain.Tag;
import com.Tblog.domain.User;
import com.Tblog.Service.BlogService;
import com.Tblog.Service.TagService;

@Controller
public class blogController {
	@Autowired
	private BlogService blogService;
	@Autowired
	private CommentRepository commentRepository;
	@Autowired
	private BlogRepository blogRepository;
	@Autowired
	private TagService tagService;
	@Autowired
	private TagRepository tagRepository;
	
	
	//博客首页
	@GetMapping("/home")
	public String getHome(Model model,HttpSession session,
			@PageableDefault(value = 5, sort = { "id" }, direction = Sort.Direction.DESC) Pageable pageable) {
		User user = (User) session.getAttribute("CURRENT_USER");
		Page<Blog> blogs = (Page<Blog>) blogService.findAll(pageable);
		model.addAttribute("user",user);
		model.addAttribute("blogs",blogs);
		model.addAttribute("Group", blogService.findBlogGroupByTime());
		return "home";
	}
	// 博客具体页
	@GetMapping("/blogs/{id}")
	public String GetIndex(@PathVariable("id") long id, Model model,HttpSession session) {
		User user = (User) session.getAttribute("CURRENT_USER");
		Blog blog = blogService.findById(id);
		List<Comment> comments = commentRepository.findByCommentBlog(blog.getId());
		model.addAttribute("user", user);
		model.addAttribute("blog", blog);
		model.addAttribute("comments", comments);
		model.addAttribute("Group", blogService.findBlogGroupByTime());
		return "item";
	}

	// 博客创建
	@GetMapping("/blogs/create")
	public String GetCreateBlog(Model model,HttpSession session) {
		User user = (User) session.getAttribute("CURRENT_USER");
		if(user!=null) {
			model.addAttribute("user", user);
			model.addAttribute("operate", "create");
			model.addAttribute("blog", new Blog());
			model.addAttribute("Group", blogService.findBlogGroupByTime());
		}
		return "create";
	}
	
	@PostMapping("/blogs")
	public String PostCreateBlog(@ModelAttribute("blog") @Valid BlogForm createBlogForm,
			 @RequestParam("tag") String tagName,HttpSession session, BindingResult result) {
		if (result.hasErrors()) {
			return "redirect:/blogs/create";
		}
		User user = (User) session.getAttribute("CURRENT_USER");
		Tag tag = new Tag(tagName);
		tag = tagRepository.save(tag);
		Blog blog = createBlogForm.toBlog(user);
		blog.setTag(tag);
		blogRepository.save(blog);
		return "redirect:/blogs/" + blog.getId();
	}
	
	
	//添加评论
	@PostMapping("/blogs/{blogId}/comments")
	@ResponseBody
	public Map<String, Object> post(HttpSession session, @PathVariable("blogId") Long blogId, @RequestBody String content) throws UnsupportedEncodingException {
		content= URLDecoder.decode(content, "utf-8");
		String str = content.substring(8);
		Comment comment = new Comment();
		Blog blog = blogService.findById(blogId);
		User user = (User) session.getAttribute("CURRENT_USER");
		Date date = new Date();
		comment.setCommentBlog(blog);
		comment.setCommentUser(user);
		comment.setCommentInfo(str);
		comment.setCommentTime(date);
		commentRepository.save(comment);
		
		LocalDateTime now = LocalDateTime.now();
		DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		Map<String, Object> result = new HashMap<String, Object>();
		  result.put("username", user.getUsername());
		  result.put("date", df.format(now));
		  result.put("content",str );
		  result.put("avatar", user.getAvatar());
		  return result;
	}

	// 更新博客
	@GetMapping("/blogs/{id}/edit")
	public String GetUpdateBlog(@PathVariable("id") long id, HttpSession session, Model model) {
		User user = (User) session.getAttribute("CURRENT_USER");
		Blog blog = blogService.findById(id);
		model.addAttribute("user", user);
		model.addAttribute("blog", blog);
		model.addAttribute("operate", "update");
		model.addAttribute("Group", blogService.findBlogGroupByTime());
		return "create";
	}

	@PutMapping("/blogs/{id}")
	public String PostUpdateBlog(@PathVariable("id") long id,  @RequestParam("tagName") String tagName,Model model, 
			@Valid BlogForm updateBlogForm,HttpSession session, BindingResult result) throws Exception {
		User user = (User) session.getAttribute("CURRENT_USER");
		if (result.hasErrors()) {
			model.addAttribute("operate", "update");
			return "redirect:/blogs/create";
		}
		Blog blog = blogRepository.findOne(id);
		Tag tag = blog.getTag();
		tag.setTagName(tagName);
		tagService.updataTag(tag);
		Blog b = updateBlogForm.toBlog(user);
		blog.setTitle(b.getTitle());
		blog.setContent(b.getContent());
		blogService.updateBlog(b);
		return "redirect:/blogs/" + id;
	}

	// 删除博客
	@DeleteMapping("/blogs/{id}")
	public String PostDeleteBlog(@PathVariable("id") long id,Model model) throws Exception {
		Blog blog = blogService.findById(id);
		blogService.deleteBlog(blog.getId());
		return "redirect:/user/admin";
	}
}