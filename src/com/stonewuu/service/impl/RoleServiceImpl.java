package com.stonewuu.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.stonewuu.dao.RoleDao;
import com.stonewuu.entity.Role;
import com.stonewuu.service.RoleService;

@Service
public class RoleServiceImpl implements RoleService{

	@Resource
	private RoleDao roleDao;
	
	@Override
	public Role create(Role role) {
		return roleDao.save(role);
	}

	@Override
	public void delete(Object roleId) {
		Role role = roleDao.find(roleId);
		if(role!=null){
			roleDao.delete(role);
		}
	}

	@Override
	public void correlationPermissions(Long roleId, Long[] permissionIds) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void uncorrelationPermissions(Long roleId, Long[] permissionIds) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(Role t) {
		// TODO Auto-generated method stub
		
	}

}
