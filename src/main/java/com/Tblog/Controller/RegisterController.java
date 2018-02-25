package com.Tblog.Controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.Tblog.Controller.Form.UserRegisterForm;
import com.Tblog.Dao.UserRepository;
import com.Tblog.domain.User;
import com.Tblog.Service.UserService;

@Controller
public class RegisterController{
	
	@Autowired
	private UserRepository userRepository;
	@Autowired 
	private UserService userService;
	
	 //用户注册
    @GetMapping("/register")
    public String GetRegister(Model model) {
    	model.addAttribute("user", new User());
    	return "register";
    }
    
    /*@PostMapping("/register")
    @ResponseBody
    public Map<String,Object> register (@RequestParam("email") String email,
    		@RequestParam("username") String username,
    		@RequestParam("password") String password,
    		@RequestParam("repeatPassword") String repeatPassword){
    	Map<String,Object> result = new HashMap<String,Object>();
    	if(userRepository.findByEmail(email)!=null) {
    		result.put("100", "邮箱已存在");
    	}else if(userRepository.findByUsername(username)!=null) {
    		result.put("101", "用户名已存在");
    	}else if(password.length()<6) {
    		result.put("102", "密码要大于6位");
    	}else if(!password.equals(repeatPassword)){
    		result.put("103", "密码不一致");
    	}
    	return result;
    }*/
    
   @PostMapping("/register")
    public String PostRegister(@ModelAttribute("user")@Valid UserRegisterForm form,BindingResult result,
    		HttpSession session,Model model) throws Exception {
    	//注册信息格式错误
    	if(result.hasErrors()) {
    		return "register";
    	}
    	//将传递的数据封装
    	User user = form.toUser();
    	//如何user为null
    	if(user==null) {
    		return "register";
    	}
    	//user不为null，则检查邮箱是否重复
    	if(userRepository.findByEmail(user.getEmail())!=null) {
			model.addAttribute("message", "邮箱已存在");
			return "register";
		}
    	
    	//user不为null，则检查用户名是否重复
    	if(userRepository.findByUsername(user.getUsername())!=null) {
			model.addAttribute("message", "用户名已存在");
			return "register";
		}
    	
    	/*//验证邮箱是否存在
    	if(SMTPMXLookup.isAddressValid(user.getEmail())==false) {
    		model.addAttribute("message", "邮箱不存在");
			return "register";
    	}*/
    	//注册成功
    	userService.register(user);
    	session.setAttribute("CURRENT_USER", user);
    	session.setMaxInactiveInterval(-1*60);
		return "redirect:/"+user.getUsername();
    }   
    
    
    //用户激活
    @PostMapping("/register/active")
    public String ActiveRegister(@RequestParam("code") String code,Model model) throws Exception {
    	User user = userService.findByCode(code);
    	if(user!=null) {
    		//查到用户并修改用户状态
    		user.setStatus(1);
    		user.setCode(null);
    		userService.updateUser(user);
    		model.addAttribute("message", "你已经激活成功!");
    		return "redirect:/admin";
    	}else {
    		return "404";
    	}	
    }   
}