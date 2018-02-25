package com.Tblog.Controller;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.Tblog.Dao.UserRepository;
import com.Tblog.utils.ImageCode;
import com.Tblog.utils.MailUtils;
import com.Tblog.utils.UUIDUtils;

@Controller
public class FindPassword {
	@Autowired  
	private UserRepository userRepository;
	@Autowired
	private MailUtils mailUtils;
	@GetMapping("/findPassword")
	public String get() {
		return "findpsw";
	}

	@GetMapping("/sendmail")
	public String get1() {
		return "sendemail";
	}

	@RequestMapping(value = "/images/imagecode")
	public String imagecode(HttpServletRequest request, HttpServletResponse response) throws Exception {
		OutputStream os = response.getOutputStream();
		Map<String, Object> map = ImageCode.getImageCode(60, 20, os);
		String simpleCaptcha = "simpleCaptcha";
		request.getSession().setAttribute(simpleCaptcha, map.get("strEnsure").toString().toLowerCase());
		request.getSession().setAttribute("codeTime", new Date().getTime());
		try {
			ImageIO.write((BufferedImage) map.get("image"), "JPEG", os);
		} catch (IOException e) {
			return "";
		}
		return null;
	}

	@PostMapping(value = "/checkcode")
	public String checkcode(Model model, @RequestParam("checkCode") String checkCode, HttpSession session,
			@RequestParam("email") String email)
			throws Exception {
		//检查email是否存在
		//TODO
		long id = 0;
		if(userRepository.exists(id)) {
			model.addAttribute("errorMsg", "邮箱不存在");
			return "findpsw";
		}
		Object cko = session.getAttribute("simpleCaptcha"); // 验证码对象
		if (cko == null) {
			model.addAttribute("errorMsg", "验证码已失效，请重新输入！");
			return "findpsw";
		}
		String captcha = cko.toString();
		Date now = new Date();
		Long codeTime = Long.valueOf(session.getAttribute("codeTime") + "");
		if (checkCode.isEmpty() || captcha == null || !(checkCode.equalsIgnoreCase(captcha))) {
			model.addAttribute("errorMsg", "验证码错误！");
			return "findpsw";
		} else if ((now.getTime() - codeTime) / 1000 / 60 > 5) {
			// 验证码有效时长为5分钟
			model.addAttribute("errorMsg", "验证码已失效，请重新输入！");
			return "findpsw";
		} 
			session.removeAttribute("simpleCaptcha");
			//发送激活邮件
			String code = UUIDUtils.getUUIDUtils();
			String subtitle = "找回密码邮件";
			String contents =String.format("<h1>这是一封来自tblog网的找回密码邮件,请点击："
					+ "</h1><h3><a href='#'>http:localhost:8080/register/ActiveService?%s</a></h3>", code)  ;
			mailUtils.sendMail(email,subtitle,contents);
			return "redirect:/sendmail";
		
	}
}