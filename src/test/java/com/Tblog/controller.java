/*package com.Tblog;

import java.util.ArrayList;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.Tblog.Pojo.Blog;
import com.Tblog.Pojo.User;
import com.Tblog.Service.BlogService;
import com.Tblog.Service.UserService;
import com.Tblog.Test.TestBase;
import com.Tblog.utils.UUIDUtils;

public class controller extends TestBase{
	@Autowired
	private UserService userService;
	@Autowired
	private BlogService blogService;
	
	@Test
	public void testController() throws Exception {
		User user = new User();
		user.setUsername("hahaha");
		user.setEmail("hahaha@gamil.com");
		user.setPassword("123455666");
		//String title = "这是你的第一篇博客";
    	String content = "亲爱的小伙伴，欢迎来到Tblog";
    	User author = user;
    	Date date = new Date();
    	blogService.createBlog(new Blog(title,content,date,author));
		User a = userService.register(user);
		ArrayList<Blog> list = (ArrayList<Blog>) blogService.findBlogsByUserId(a.getId());
		System.out.println(list.get(0).getTitle());
		//System.out.println(a.getId());
		//System.out.println(a.getUsername());
		System.out.println(UUIDUtils.getUUIDUtils());
		System.out.println(user.getCode());
	}
}
*/

