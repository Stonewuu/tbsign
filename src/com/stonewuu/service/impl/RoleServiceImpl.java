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
	public Role createRole(Role role) {
		return roleDao.save(role);
	}

	@Override
	public void deleteRole(Long roleId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void correlationPermissions(Long roleId, Long[] permissionIds) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void uncorrelationPermissions(Long roleId, Long[] permissionIds) {
		// TODO Auto-generated method stub
		
	}

}
