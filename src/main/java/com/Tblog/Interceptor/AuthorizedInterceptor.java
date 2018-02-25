package com.Tblog.Interceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.Tblog.Dao.UserRepository;
import com.Tblog.domain.User;
import com.Tblog.utils.CookieTool;

public class AuthorizedInterceptor implements HandlerInterceptor {

	@Autowired
	private UserRepository userRepository;

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		Cookie cookieEmail = CookieTool.getCookie(request, "loginEmail");
		Cookie cookiePassword = CookieTool.getCookie(request, "loginPsw");
		if (cookieEmail != null && cookiePassword != null && cookieEmail.getValue() != "" && cookiePassword.getValue() != "") {
			String loginEmail = cookieEmail.getValue();
			String loginPsw = cookiePassword.getValue();

			// 检查客户端保存的用户
			User currentUser = userRepository.findByEmailAndPassword(loginEmail, loginPsw);

			// 如果用户被删除
			if (currentUser == null) {
				// 清除Cookie
				CookieTool.addCookie(response, "loginEmail", null, 0);
				CookieTool.addCookie(response, "loginPasswoord", null, 0);
				response.sendRedirect("/login");
				request.getSession().setAttribute("errorInfo", "请登录！");
			} else {
				// 用户存在
				User user = (User) request.getSession().getAttribute("currentUser");
				if (user == null) {
					request.getSession().setAttribute("currentUser", currentUser);
				} else {
					// 用户登录
					if (user.getUsername().equals(currentUser.getUsername())) {
						request.getSession().setAttribute("currentUser", currentUser);
					} else {
						// 如果登录信息与cookie不一致
						request.getSession().setAttribute("currentUser", currentUser);
					}
				}
			}

		} else {
			// 如果没有记住密码或者退出了账号
			User u = (User) request.getSession().getAttribute("currentUser");
			if (u == null) {
				response.sendRedirect("/login");
				return false;
			} else {
				// 如果已登陆，执行下一步
			}
		}
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub

	}

}