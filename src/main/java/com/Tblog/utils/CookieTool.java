package com.Tblog.utils;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
public class CookieTool {
	
	//设置cookie
	public static void addCookie(HttpServletResponse response, String name,String value,int maxAge) throws Exception {
		Cookie cookie = new Cookie(name,value);
		cookie.setPath("/");
		if(maxAge>0) {
			cookie.setMaxAge(maxAge);
		}
		response.addCookie(cookie);
	}
	
	
	//将cookie封装到Map中
	public static Map<String,Cookie> ReadCookieMap(HttpServletRequest request){
		Map<String,Cookie> MapCookie = new HashMap<String,Cookie>();
		Cookie[] cookies = request.getCookies();
		for(Cookie cookie:cookies) {
			MapCookie.put(cookie.getName(), cookie);
		}
		return MapCookie;
	}
	
	//通过名字获取cookie
	public static Cookie getCookie(HttpServletRequest request,String name) {
		Map<String,Cookie> MapCookie = ReadCookieMap(request);
		if(MapCookie.containsKey("name")) {
			Cookie cookie = MapCookie.get(name);
			return cookie;
		}else {
			return null;
		}	
	}
}