package com.stonewuu.service;

import com.stonewuu.entity.Permission;

public interface PermissionService {

	public Permission createPermission(Permission permission);

	public void deletePermission(Long permissionId);
}
