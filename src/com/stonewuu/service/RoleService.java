package com.stonewuu.service;

import com.stonewuu.entity.Role;

public interface RoleService extends GeneralService<Role> {


	/**
	 * @Title: correlationPermissions
	 * @Description: 添加 角色-权限之间关系
	 * @author stonewuu 2017年1月11日 下午3:02:24
	 *
	 * @param roleId
	 * @param permissionIds
	 */
	public void correlationPermissions(Long roleId, Long[] permissionIds);

	/**
	 * @Title: uncorrelationPermissions
	 * @Description: 移除 角色-权限之间关系
	 * @author stonewuu 2017年1月11日 下午3:01:58
	 *
	 * @param roleId
	 * @param permissionIds
	 */
	public void uncorrelationPermissions(Long roleId, Long[] permissionIds);
}
