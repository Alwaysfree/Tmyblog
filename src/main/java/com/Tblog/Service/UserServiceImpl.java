package com.Tblog.Service;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Tblog.Dao.BlogRepository;
import com.Tblog.Dao.TagRepository;
import com.Tblog.Dao.UserRepository;
import com.Tblog.domain.Blog;
import com.Tblog.domain.Tag;
import com.Tblog.domain.User;
import com.Tblog.utils.MailUtils;
import com.Tblog.utils.StringUtil;
import com.Tblog.utils.UUIDUtils;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private MailUtils mailUtils;
	@Autowired
	private TagRepository tagRepository;
	@Autowired
	private BlogRepository blogRepository;
	/**
	 * 通过用户名找到User
	 * 
	 * @param username
	 * return
	 */
	// 通过用户名找到User
	public User getByUsername(String username) {
		return userRepository.findByUsername(username);
	}

	/**
	 * 用户注册
	 * 
	 * @param user
	 * @throws Exception
	 * turn user
	 */
	public User register(User user) throws Exception {
		// MD5加密
		try {
			Date now = new Date();
			user.setNow(now);
			// Md5加盐加密
			user.setPassword(StringUtil.encodeByMd5(user.getPassword()));
			user.setStatus(0);// 0代表未激活，1代表已激活
			String code = UUIDUtils.getUUIDUtils();
			user.setCode(code);
			userRepository.save(user);
			//添加一条博客
			Tag tag = new Tag();
			tag.setTagName("Java");
			tagRepository.save(tag);
			String title = "这是你的第一条博客";
			String content = "欢迎小伙伴来到Tblog";
			Blog blog = new Blog(title,content,new Date(),user,tag);
			blogRepository.save(blog);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		// 发送激活邮件
		String subtitle = "激活邮件";
		String contents = String.format("<h1>这是一封来自tblog网的激活邮件,激活请点击："
				+ "</h1><h3><a href='/register'>http:tblog/register/ActiveService?%s</a></h3>", user.getCode());
		mailUtils.sendMail(user.getEmail(), subtitle, contents);
		return user;
	}

	/**
	 * 用户登录
	 * 
	 * @param email
	 * @param password
	 * @param return
	 */
	// 用户登录
	public User login(String email, String password) {
		String MD5Password;
		try {
			// MD5加密
			MD5Password = StringUtil.encodeByMd5(password);
			User user = (User) userRepository.findByEmailAndPassword(email, MD5Password);
			if (user != null) {
				return user;
			}
		} catch (NoSuchAlgorithmException e) {
			//
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			//
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * 根据用户Id删除用户
	 * 
	 * @param id
	 * @throws Exception
	 */
	public void deleteUser(long id) throws Exception {
		try {
			User user = userRepository.findOne(id);
			userRepository.delete(user);
		} catch (Exception e) {
			//
		}
	}

	/**
	 * 更新用户
	 * 
	 * @param user
	 * @throws Exception
	 * return user
	 */
	public User updateUser(User user) throws Exception {
		try {
			User u = userRepository.findOne(user.getId());
			u.setId(user.getId());
			u.setUsername(user.getUsername());
			u.setEmail(user.getEmail());
			u.setPassword(user.getPassword());
			u.setDescription(user.getDescription());
			u.setAvatar(user.getAvatar());
			userRepository.saveAndFlush(user);
			return u;
		} catch (Exception e) {
			//
		}
		return null;
	}

	/**
	 * 通过激活码找到User
	 * 
	 * @param code
	 * return User
	 */
	@Override
	public User findByCode(String code) {
		User user = (User) userRepository.findByCode(code);
		return user;
	}

	/**
	 * 通过用户id找到User
	 * 
	 * @param id
	 * return User
	 */
	@Override
	public User getByUserId(long id) {
		User user = userRepository.findOne(id);
		return user;
	}
}