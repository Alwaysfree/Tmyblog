package com.Tblog.utils;

import java.util.UUID;

import org.springframework.stereotype.Component;
@Component
public class UUIDUtils {
	//生成随机字符
	public static String getUUIDUtils() {
		return UUID.randomUUID().toString().replace("-", "");
	}
}