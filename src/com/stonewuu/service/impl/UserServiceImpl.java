package com.stonewuu.service.impl;

import java.util.Set;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.springframework.stereotype.Service;

import com.stonewuu.dao.UserDao;
import com.stonewuu.entity.User;
import com.stonewuu.helper.PasswordHelper;
import com.stonewuu.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	
	@Resource
	private UserDao userDao;
	
	@Resource
	private PasswordHelper passHelper;
	
	@Override
	@Transactional
	public User createUser(User user) {
		if(user==null || StringUtils.isEmpty(user.getName()) ){
			return null;
		}
		passHelper.encryptPassword(user);
		return userDao.createUser(user);
	}

	@Override
	@Transactional
	public void changePassword(Long userId, String newPassword) {
		User user = userDao.getUser(userId);
		if(user==null){
			throw new UnknownAccountException();
		}
		user.setPassword(newPassword);
		passHelper.encryptPassword(user);
		userDao.update(user);
	}

	@Override
	@Transactional
	public void changePassword(Long userId, String oldPassword, String newPassword) {
		User user = userDao.getUser(userId);
		if(user==null){
			throw new UnknownAccountException();
		}
		User oldUser = new User();
		oldUser.setPassword(oldPassword);
		oldUser.setSalt(user.getSalt());
		passHelper.encryptPassword(oldUser);
		//校验旧密码是否正确
		if(oldUser.getPassword().equals(user.getPassword())){
			user.setPassword(newPassword);
			passHelper.encryptPassword(user);
			userDao.update(user);
		}else{
			//抛出密码不正确异常
			throw new IncorrectCredentialsException();
		}
		
	}

	@Override
	public void correlationRoles(Long userId, Long[] roleIds) {
		// TODO Auto-generated method stub

	}

	@Override
	public void uncorrelationRoles(Long userId, Long[] roleIds) {
		// TODO Auto-generated method stub

	}

	@Override
	public User findByUsername(String userName) {
		return userDao.getUser(userName);
	}

	@Override
	public Set<String> findRoles(String username) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<String> findPermissions(String username) {
		// TODO Auto-generated method stub
		return null;
	}

}
