package com.stonewuu.test;

import java.util.HashSet;
import java.util.Set;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.stonewuu.entity.Role;
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
		
		Role role = new Role();
		role.setName("角色1");
		role.setRole("role1");
		role = roleService.createRole(role);

		User user = new User();
		user.setName("测试");
		user.setPassword("123456");
		Set<Role> roles = new HashSet<>();
		roles.add(role);
		user.setRoles(roles);
		userService.createUser(user);
		
	}
}
