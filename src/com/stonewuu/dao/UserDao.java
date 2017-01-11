package com.stonewuu.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.stonewuu.entity.User;

@Repository
public class UserDao extends GeneralDAO<User>{

	public User getUser(String userName){
		List<User> list =findByProperty("name", userName);
		if(list!=null && !list.isEmpty()){
			return list.get(0);
		}
		return null;
	}
	public User getUser(Long id){
		List<User> list =findByProperty("id", id);
		if(list!=null && !list.isEmpty()){
			return list.get(0);
		}
		return null;
	}
	
	public User createUser(User user){
		return save(user);
	}
}
