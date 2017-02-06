package com.stonewuu.test;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.stonewuu.entity.User;
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
		User user = new User();
		user.setName("test");
		user.setPassword("test");
		userService.create(user);
	}
}
