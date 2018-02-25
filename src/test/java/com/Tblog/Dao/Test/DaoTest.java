package com.Tblog.Dao.Test;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.Tblog.TblogApplication;
import com.Tblog.Dao.BlogRepository;
import com.Tblog.Dao.TagRepository;
import com.Tblog.Dao.UserRepository;
import com.Tblog.domain.Blog;
import com.Tblog.domain.Tag;
import com.Tblog.domain.User;
import com.Tblog.Test.TestBase;
import com.Tblog.utils.StringUtil;
@SpringBootTest(classes = TblogApplication.class)
public class DaoTest extends TestBase {
	@Autowired
	private TagRepository tagRepository;
	@Test
	public void testDao() throws NoSuchAlgorithmException, UnsupportedEncodingException {
		/*User user = userRepository.findByEmailAndPassword("admin@tblog.com", StringUtil.encodeByMd5("admin"));
		System.out.println(user.getId());*/
		Tag tag = new Tag();
		tag.setTagName("博客");
		tag = tagRepository.save(tag);
		System.out.println(tag.getId());
		Tag tag1 = new Tag();
		tag.setTagName("博客");
		tag = tagRepository.save(tag1);
		System.out.println(tag1.getId());
	}
}


