package com.stonewuu.test;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.stonewuu.service.RoleService;
import com.stonewuu.service.UserService;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "file:WebContent/WEB-INF/applicationContext.xml")
public class BaseTest {
	@Resource
	private UserService userService;
	@Resource
	private RoleService roleService;
	
	@Test
	public void test1(){
		StringBuffer str = new StringBuffer("1");
		str.delete(str.length()-1, str.length());
		System.out.println(str);
	}
}
