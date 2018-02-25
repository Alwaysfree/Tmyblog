package com.Tblog.Controller;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.Tblog.domain.EsBlog;
import com.Tblog.domain.User;
import com.Tblog.Service.EsBlogService;

@Controller
public class ResearchController{
	@Autowired 
	private EsBlogService esBlogService;
	
	//查找
    @GetMapping("/search")
    public String search(@PageableDefault(value = 10, sort = { "id" }, direction = Sort.Direction.DESC) Pageable pageable,
    		@RequestParam("keyword") String key,Model model,HttpSession session) {
    	User user= (User) session.getAttribute("CURRENT_USER");
		model.addAttribute("user", user);
        Page<EsBlog>listEsBlogs =  esBlogService.listsEsBlogs(key, pageable);
        model.addAttribute("blogs", listEsBlogs);
        return "search";
    }
}