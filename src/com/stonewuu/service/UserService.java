package com.stonewuu.service;

import java.util.Set;

import com.stonewuu.entity.User;

public interface UserService extends GeneralService<User>{

	/**
	 * @Title: changePassword
	 * @Description: 修改密码
	 * @author stonewuu 2017年1月11日 下午2:58:52
	 *
	 * @param userId
	 * @param newPassword
	 */
	public void changePassword(Long userId, String newPassword);
	
	/**
	 * @Title: changePassword
	 * @Description: 通过旧密码修改密码
	 * @author stonewuu 2017年1月11日 下午3:01:04
	 *
	 * @param userId
	 * @param oldPassword
	 * @param newPassword
	 */
	public void changePassword(Long userId, String oldPassword,String newPassword);

	/**
	 * @Title: correlationRoles
	 * @Description: 添加用户-角色关系
	 * @author stonewuu 2017年1月11日 下午2:58:55
	 *
	 * @param userId
	 * @param roleIds
	 */
	public void correlationRoles(Long userId, Long[] roleIds);

	/**
	 * @Title: uncorrelationRoles
	 * @Description: 移除用户-角色关系
	 * @author stonewuu 2017年1月11日 下午2:58:59
	 *
	 * @param userId
	 * @param roleIds
	 */
	public void uncorrelationRoles(Long userId, Long[] roleIds);

	/**
	 * @Title: findByUsername
	 * @Description: 根据用户名查找用户
	 * @author stonewuu 2017年1月11日 下午2:59:03
	 *
	 * @param username
	 * @return
	 */
	
	public User findByUserName(String userName);
	/**
	 * @Title: findByUsername
	 * @Description: 根据用户名或者邮箱查找用户
	 * @author stonewuu 2017年1月11日 下午2:59:03
	 *
	 * @param username
	 * @return
	 */
	public User findByUserNameOrEmail(String userName,String email);

	/**
	 * @Title: findRoles
	 * @Description: 根据用户名查找其角色
	 * @author stonewuu 2017年1月11日 下午2:59:06
	 *
	 * @param username
	 * @return
	 */
	public Set<String> findRoles(String username);

	/**
	 * @Title: findPermissions
	 * @Description: 根据用户名查找其权限
	 * @author stonewuu 2017年1月11日 下午2:59:09
	 *
	 * @param username
	 * @return
	 */
	public Set<String> findPermissions(String username);

	/**
	 * 
	 * @Title: updateBdInfo
	 * @Description: 更新用户绑定信息
	 * @author stonewuu 2017年1月21日 下午5:52:20
	 *
	 * @param user
	 * @return
	 */
	public User updateBdInfo(User user);

}
