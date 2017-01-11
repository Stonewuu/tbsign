package com.stonewuu.shiro;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Service;

import com.stonewuu.dao.UserDao;
import com.stonewuu.entity.Permission;
import com.stonewuu.entity.Role;
import com.stonewuu.entity.User;

@Service
public class ShiroRealm extends AuthorizingRealm {

	@Resource
	private UserDao userDao;

	/**
	 * 登陆第二步,通过用户信息将其权限和角色加入作用域中,达到验证的功能
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		// 根据用户配置用户与权限
		if (principals == null) {
			throw new AuthorizationException("PrincipalCollection method argument cannot be null.");
		}
		String name = (String) getAvailablePrincipal(principals);
		List<String> rolesStr = new ArrayList<String>();
		List<String> permissions = new ArrayList<String>();
		// 通过当前登陆用户的姓名查找到相应的用户的所有信息
		User user = userDao.getUser(name);
		if (user.getName().equals(name)) {
			Set<Role> roles = user.getRoles();
			//获取角色
			if (roles != null) {
				for(Role role : roles){
					// 装配用户的角色
					rolesStr.add(role.getRole());
					//获取权限
					Set<Permission> permiss = role.getPermissions();
					if(permiss != null){
						for(Permission p : permiss){
							//装配用户的权限
							permissions.add(p.getPermission());
						}
					}
				}
			}
		} else {
			throw new AuthorizationException();
		}
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		// 为用户设置角色和权限
		info.addRoles(rolesStr);
		info.addStringPermissions(permissions);
		return info;
	}

	/**
	 * 登录第一步，验证当前登录的Subject
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken)
			throws AuthenticationException {
		// 登陆后的操作,此处为登陆有的第一步操作,从LoginController.login中调用了此处的token
		UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
		System.out.println("token is :" + token);
		// 简单默认一个用户,实际项目应User user =
		// userService.getByAccount(token.getUsername());
		// 下面通过读取token中的数据重新封装了一个user
		User user = userDao.getUser(token.getUsername());
		if (user == null) {
			throw new AuthorizationException("该用户不存在！");
		}
		if(Boolean.TRUE.equals(user.getLock())) {
            throw new LockedAccountException(); //帐号被锁定  
        }
		SimpleAuthenticationInfo info = null;
		if (user.getName().equals(token.getUsername())) {
			info = new SimpleAuthenticationInfo(user.getName(), user.getPassword(), getName());
		}
		// 将该User村放入session作用域中
		// this.setSession("user", user);
		return info;
	}

	/**
	 * 将一些数据放到ShiroSession中,以便于其它地方使用
	 * 比如Controller,使用时直接用HttpSession.getAttribute(key)就可以取到
	 */
	private void setSession(Object key, Object value) {
		Subject currentUser = SecurityUtils.getSubject();
		if (null != currentUser) {
			Session session = currentUser.getSession();
			System.out.println("Session默认超时时间为[" + session.getTimeout() + "]毫秒");
			if (null != session) {
				session.setAttribute(key, value);
			}
		}
	}

	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

}
