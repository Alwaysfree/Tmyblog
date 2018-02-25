package com.Tblog.Test;

import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;


//SpringJUnit支持，由此引入Spring-Test框架支持！
@RunWith(SpringJUnit4ClassRunner.class)
//由于是Web项目，Junit需要模拟ServletContext，因此我们需要给我们的测试类加上@WebAppConfiguration。
//@SpringApplicationConfiguration(classes = TblogApplication.class)
//测了，不用一下配置也可以，不知道为毛
@WebAppConfiguration
public class TestBase{
}