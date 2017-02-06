package com.stonewuu.shiro;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.springframework.stereotype.Service;

import com.stonewuu.entity.Permission;
import com.stonewuu.entity.Role;
import com.stonewuu.entity.User;
import com.stonewuu.helper.PasswordHelper;
import com.stonewuu.service.UserService;

@Service
public class ShiroRealm extends AuthorizingRealm {

	@Resource
	private UserService userService;
	@Resource
	private PasswordHelper passHelper;

	/**
	 * 登陆第二步,通过用户信息将其权限和角色加入作用域中,达到验证的功能
	 */
	@Override
	@Transactional
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		// 根据用户配置用户与权限
		if (principals == null) {
			throw new AuthorizationException("PrincipalCollection method argument cannot be null.");
		}
		String name = (String) getAvailablePrincipal(principals);
		List<String> rolesStr = new ArrayList<String>();
		List<String> permissions = new ArrayList<String>();
		// 通过当前登陆用户的姓名查找到相应的用户的所有信息
		User user = userService.findByUserName(name);
		if (user.getName().equals(name)) {
			List<Role> roles = user.getRoles();
			//获取角色
			if (roles != null) {
				for(Role role : roles){
					// 装配用户的角色
					rolesStr.add(role.getName());
					//获取权限
					List<Permission> permiss = role.getPermissions();
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
	@Transactional
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken)
			throws AuthenticationException {
		// 登陆后的操作,此处为登陆有的第一步操作,从LoginController.login中调用了此处的token
		UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
		// 查询用户
		User user = userService.findByUserNameOrEmail(token.getUsername(),token.getUsername());

		if (user == null) {
			throw new UnknownAccountException("该用户不存在！");
		}
		if(Boolean.TRUE.equals(user.getLocked())) {
            throw new LockedAccountException(); //帐号被锁定  
        }
		
		SimpleAuthenticationInfo info = null;
		//如果是用户名
		if (user.getName().equals(token.getUsername())) {
			info = new SimpleAuthenticationInfo(user.getName(), user.getPassword(),ByteSource.Util.bytes(user.getSalt()), getName());
		}
		//如果是邮箱
		if (user.getEmail().equals(token.getUsername())) {
			info = new SimpleAuthenticationInfo(user.getEmail(), user.getPassword(),ByteSource.Util.bytes(user.getSalt()), getName());
		}
		//保存用户信息
		Subject currentUser = SecurityUtils.getSubject(); 
		Session session = currentUser.getSession();
		session.setAttribute("currentUser",user);
		return info;
	}

}
