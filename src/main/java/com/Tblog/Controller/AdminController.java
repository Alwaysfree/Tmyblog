package com.Tblog.Controller;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.Tblog.domain.User;
import com.Tblog.Service.BlogService;

@Controller
public class AdminController{

	@Autowired
	private BlogService blogService;
	@GetMapping("/user/admin")
	public String get(Model model,@ModelAttribute("message") String message,
			@PageableDefault(value = 5, sort = { "id" }, direction = Sort.Direction.DESC) Pageable pageable,
			HttpSession session) {
		User user= (User) session.getAttribute("CURRENT_USER");
		model.addAttribute("user", user);
		model.addAttribute("blogs", blogService.findAllBlogsByUserId(user.getId(),pageable));
		return "blogAdmin";
	}
}