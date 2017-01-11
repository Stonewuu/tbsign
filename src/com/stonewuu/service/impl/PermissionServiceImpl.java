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
	public Permission createPermission(Permission permission) {
		return permissDao.save(permission);
	}

	@Override
	public void deletePermission(Long permissionId) {
		Permission p = permissDao.find(permissionId);
		if (p != null) {
			permissDao.delete(p);
		}
	}

}
