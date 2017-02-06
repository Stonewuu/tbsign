package com.stonewuu.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.stonewuu.entity.User;

@Repository
public class UserDao extends GeneralDAO<User>{

	/**
	 * @Title: getUser
	 * @Description: 通过用户名查询用户
	 * @author stonewuu 2017年1月17日 下午6:05:56
	 *
	 * @param userName
	 * @return
	 */
	public User getUser(String userName){
		List<User> list =findByProperty("name", userName);
		if(list!=null && !list.isEmpty()){
			return list.get(0);
		}
		return null;
	}
	
	/**
	 * @Title: getUser
	 * @Description: 通过用户名或者邮箱查询用户
	 * @author stonewuu 2017年1月17日 下午6:05:41
	 *
	 * @param userName
	 * @param email
	 * @return
	 */
	public User getUser(String userName,String email){
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("name", userName);
		param.put("email", email);
		List<User> list =findByPropertys("name = :name or email = :email", param);
		if(list!=null && !list.isEmpty()){
			return list.get(0);
		}
		return null;
	}
	/**
	 * 
	 * @Title: getUser
	 * @Description: 通过用户ID查询用户
	 * @author stonewuu 2017年1月17日 下午6:06:07
	 *
	 * @param id
	 * @return
	 */
	public User getUser(Long id){
		List<User> list =findByProperty("id", id);
		if(list!=null && !list.isEmpty()){
			return list.get(0);
		}
		return null;
	}
	
	/**
	 * 
	 * @Title: createUser
	 * @Description: 创建用户
	 * @author stonewuu 2017年1月17日 下午6:06:27
	 *
	 * @param user
	 * @return
	 */
	public User createUser(User user){
		return save(user);
	}
}
