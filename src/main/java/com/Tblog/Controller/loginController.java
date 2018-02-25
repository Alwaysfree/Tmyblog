/*package com.Tblog.Controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import com.Tblog.Pojo.User;
import com.Tblog.Service.UserService;
@Controller
public class Userlogin{
	@Autowired 
	private UserService userService;
	//用户登录
	@GetMapping("/login")
	public String GetLogin() {
		return "login";	
	}
	
	@PostMapping("/login")
	@ResponseBody
	public String PostLogin(HttpSession session,@RequestBody LoginForm form) {
		LoginStatus status = new LoginStatus();
		System.out.println(form.username);
		User currentUser = userService.login(form.username, form.password);
        if (currentUser!=null){
            status.status = "success";
            status.message = "登录成功";
            session.setAttribute("CURRENT_USER", currentUser);
            String username = currentUser.getUsername();		
            return "about";
        } else {
            status.status = "fail";
            status.message = "登录失败，用户名或密码不正确";
            return "login";
        }
    }
  
    class LoginForm {
        public String username;
        public String password;
    }
    class LoginStatus {
        public String status;
        public String message;
    }
    
    
    
  //用户登出
    @PostMapping("/logout")
    public String  PostLogout(HttpSession session) {
    	session.removeAttribute("currentUser");
    	return "about";
    }
    
    

    //忘记密码
    //TODO
    
}*/

package com.Tblog.Controller;
import java.util.Optional;

import javax.servlet.http.Cookie;
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

import com.Tblog.domain.User;
import com.Tblog.Service.UserService;
import com.Tblog.utils.CookieTool;


@Controller
public class loginController {

	@Autowired
	private UserService userService;
	//登录
	@GetMapping("/login")
	public String get(@RequestParam("next") Optional<String> next) {
		return "login";
	}

	@PostMapping("/login")
	public String Post(@RequestParam("email") String email, @RequestParam("password") String password,
			@RequestParam("next") Optional<String> next, HttpSession session, Model model, 
			HttpServletRequest request,HttpServletResponse response) throws Exception {
		User currentUser = userService.login(email, password);
		if (currentUser == null) {
			model.addAttribute("message", "用户名或密码错误，请重新登录！");
			return "login";
		}
		String[] values = request.getParameterValues("remember-me");
		if (values != null && !values[0].isEmpty()) {
			// 这里表示用户勾选了Remember Me
			int loginMaxAge = 30 * 24 * 60 * 60;
			CookieTool.addCookie(response, "loginEmail", email, loginMaxAge);
			CookieTool.addCookie(response, "loginPassword", password, loginMaxAge);
		}
		String name = currentUser.getUsername();
		session.setAttribute("CURRENT_USER", currentUser);
		session.setMaxInactiveInterval(-1*60);
		return "redirect:".concat(next.orElse("/" + name));
	}
	
	//退出登录
	@RequestMapping("/logout")
	public String logout( HttpSession session, HttpServletResponse response, Model model){
		User loginUser = (User) session.getAttribute("CURRENT_USER");
		//删除登录cookie
		Cookie emailCookie = new Cookie("loginUserEmail", loginUser.getEmail());
		Cookie passwordCookie = new Cookie("loginPassword", loginUser.getPassword());
		emailCookie.setMaxAge(0);
		emailCookie.setPath("/");
		passwordCookie.setMaxAge(0);
		passwordCookie.setPath("/");
		response.addCookie(emailCookie);
		response.addCookie(passwordCookie);
		session.removeAttribute("CURRENT_USER");
		return "redirect:/about";
	}


}