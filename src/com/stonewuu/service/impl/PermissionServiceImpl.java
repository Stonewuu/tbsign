package com.stonewuu.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.stonewuu.dao.PermissionDao;
import com.stonewuu.entity.Permission;
import com.stonewuu.service.PermissionService;

@Service
public class PermissionServiceImpl implements PermissionService {

	@Resource
	private PermissionDao permissDao;

	@Override
	public Permission create(Permission permission) {
		return permissDao.save(permission);
	}

	@Override
	public void delete(Object permissionId) {
		Permission p = permissDao.find(permissionId);
		if (p != null) {
			permissDao.delete(p);
		}
	}

	@Override
	public void update(Permission t) {
		// TODO Auto-generated method stub
		
	}

}
