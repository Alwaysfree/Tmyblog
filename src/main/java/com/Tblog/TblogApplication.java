package com.Tblog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;

@SpringBootApplication
@Controller
public class TblogApplication{
	public static void main(String[] args)throws Exception {
		SpringApplication.run(TblogApplication.class, args);
	}
}

  