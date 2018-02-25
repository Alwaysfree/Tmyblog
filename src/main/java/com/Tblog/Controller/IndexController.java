package com.Tblog.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.Tblog.domain.Blog;
import com.Tblog.domain.User;
import com.Tblog.Service.BlogService;
import com.Tblog.Service.UserService;
import org.springframework.data.domain.Sort;

@Controller
public class IndexController {
	@Autowired
	private BlogService blogService;
	@Autowired
	private UserService userService;

	@GetMapping("/about")
	public String about() {
		return "about";
	}
	
	//博客列表
	@GetMapping("/{username:[a-z0-9_]+}")
	public String BlogsProfile(@PathVariable("username") String Name,Model model,
			@PageableDefault(value = 5, sort = { "id" }, direction = Sort.Direction.DESC) Pageable pageable) {
		User user = userService.getByUsername(Name);
		Page<Blog> blogs = blogService.findAllBlogsByUserId(user.getId(),pageable);
		List<Object[]>  archiveBlogs = blogService.findBlogGroupByTime(); 
		model.addAttribute("user",user);
		model.addAttribute("blogs", blogs);
		model.addAttribute("archiveBlogs",archiveBlogs);
		model.addAttribute("Group", blogService.findBlogGroupByTime());
		return "list";
	}
	
	//发送邮件成功
	@GetMapping("/sendemail")
	public String sendemail() {
		return "sendemail";
	}

}
