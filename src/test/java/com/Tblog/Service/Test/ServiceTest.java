/*package com.Tblog.Service.Test;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;

import com.Tblog.TblogApplication;
import com.Tblog.domain.Blog;
import com.Tblog.domain.User;
import com.Tblog.Service.BlogService;
import com.Tblog.Service.UserService;
import com.Tblog.Test.TestBase;
import com.Tblog.utils.StringUtil;
@SpringBootTest(classes = TblogApplication.class)
public class ServiceTest extends TestBase{
	@Autowired
	private UserService userService;
	@Autowired
	private BlogService blogService;
	
	@Test
	public void testService() throws Exception{
		User user = userService.getByUsername("service");
		System.out.println(user.getId());
		Blog blog = blogService.findById(4);
		System.out.println(blog.getTitle());
		Blog blog = new Blog("哈哈","尼玛的",new Date(),user);
		blogService.saveBlog(blog);
		System.out.println(blog.getId());
		System.out.println(StringUtil.encodeByMd5("admin"));
		System.out.println(StringUtil.encodeByMd5("reson"));
		System.out.println(StringUtil.encodeByMd5("test01"));
		System.out.println(StringUtil.encodeByMd5("test02"));
		System.out.println(StringUtil.encodeByMd5("test03"));
		System.out.println(StringUtil.encodeByMd5("test04"));
		System.out.println(StringUtil.encodeByMd5("test05"));
		User user = new User("service","123456");
		userService.register(user);
		System.out.println(user.getId());
	}
}

*/