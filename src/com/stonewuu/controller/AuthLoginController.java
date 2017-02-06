package com.stonewuu.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.ExpiredCredentialsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.stonewuu.entity.User;
import com.stonewuu.service.UserService;

@Controller
@RequestMapping(value = "/auth/")
public class AuthLoginController extends BaseController {
	private static final Log log = LogFactory.getLog(AuthLoginController.class);
	
	@Resource
	private UserService userSerivce;

	/**
	 * 
	 * @Title: login
	 * @Description: 登录页面
	 * @author stonewuu 2017年1月19日 下午3:37:38
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping(value = { "login" }, method = RequestMethod.GET)
	public ModelAndView login(HttpServletRequest request) {
		ModelAndView view = super.getModel(request);
		Subject subject = SecurityUtils.getSubject();
		if(subject.isAuthenticated()){
			return new ModelAndView("redirect:/");
		}else{
			view.setViewName("/auth/login");
		}
		return view;
	}
	
	/**
	 * 
	 * @Title: loginIn
	 * @Description: 提交登陆
	 * @author stonewuu 2017年1月19日 下午3:37:30
	 *
	 * @param request
	 * @param currUser
	 * @return
	 */
	@RequestMapping(value = { "loginIn" }, method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> loginIn(HttpServletRequest request, User currUser) {
		ModelAndView view = super.getModel(request);
		Map<String, Object> map = new HashMap<>();
		map = view.getModel();
		Subject subject = SecurityUtils.getSubject();
		if(subject.isAuthenticated()){
			return map;
		}
		UsernamePasswordToken token = new UsernamePasswordToken(currUser.getName(), currUser.getPassword());
		String msg = "";
		try {
			subject.login(token);
			map.put("status", true);
		} catch (IncorrectCredentialsException e) {
			msg = "登录密码错误。";
			loginError(map, subject, msg);
		} catch (ExcessiveAttemptsException e) {
			msg = "登录失败次数过多";
			loginError(map, subject, msg);
		} catch (LockedAccountException e) {
			msg = "帐号 " + token.getPrincipal() + " 已被锁定";
			loginError(map, subject, msg);
		} catch (DisabledAccountException e) {
			msg = "帐号 " + token.getPrincipal() + " 已被禁用";
			loginError(map, subject, msg);
		} catch (ExpiredCredentialsException e) {
			msg = "帐号 " + token.getPrincipal() + " 已过期";
			loginError(map, subject, msg);
		} catch (UnknownAccountException e) {
			msg = "帐号 " + token.getPrincipal() + " 不存在";
			loginError(map, subject, msg);
		} catch (UnauthorizedException e) {
			msg = "您没有得到相应的授权！" + e.getMessage();
			loginError(map, subject, msg);
		} catch (AuthorizationException e) {
			msg = "登录失败！" + e.getMessage();
			loginError(map, subject, msg);
		} catch (AuthenticationException e) {
			msg = "登录失败！" + e.getMessage();
			loginError(map, subject, msg);
		}
		return map;
	}

	/**
	 * 
	 * @Title: loginError
	 * @Description: 处理登录错误
	 * @author stonewuu 2017年1月19日 下午3:37:18
	 *
	 * @param map
	 * @param subject
	 * @param msg
	 */
	private void loginError(Map<String, Object> map, Subject subject, String msg) {
		map.put("status", false);
		map.put("msg", msg);
		log.warn(msg);
	}
	
	/**
	 * 
	 * @Title: regist
	 * @Description: 注册页面
	 * @author stonewuu 2017年1月19日 下午3:37:06
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping(value = { "regist" }, method = RequestMethod.GET)
	public ModelAndView regist(HttpServletRequest request) {
		ModelAndView view = super.getModel(request);
		Subject subject = SecurityUtils.getSubject();
		if(subject.isAuthenticated()){
			return new ModelAndView("redirect:/");
		}else{
			view.setViewName("/auth/regist");
		}
		return view;
	}

	/**
	 * 
	 * @Title: registIn
	 * @Description: 提交注册
	 * @author stonewuu 2017年1月19日 下午3:36:51
	 *
	 * @param request
	 * @param currUser
	 * @return
	 */
	@RequestMapping(value = { "registIn" }, method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> registIn(HttpServletRequest request, User currUser){
		ModelAndView view = super.getModel(request);
		Map<String,Object> map = view.getModel();
		Subject subject = SecurityUtils.getSubject();
		if(subject.isAuthenticated()){
			return map;
		}
		if(StringUtils.isEmpty(currUser.getName())){
			map.put("msg","用户名不能为空！");
			map.put("status", false);
			return map;
		}
		if(StringUtils.isEmpty(currUser.getPassword())){
			map.put("msg","密码不能为空！");
			map.put("status", false);
			return map;
		}
		if(StringUtils.isEmpty(currUser.getEmail())){
			map.put("msg","邮箱不能为空！");
			map.put("status", false);
			return map;
		}
		User user = userSerivce.findByUserNameOrEmail(currUser.getName(),currUser.getEmail());
		if(user!=null){
			String msg = "该用户名已存在！";
			map.put("msg",msg);
			map.put("status", false);
			return map;
		}
		UsernamePasswordToken token = new UsernamePasswordToken(currUser.getName(), currUser.getPassword());
		try {
			user = userSerivce.create(currUser);
			subject.login(token);
			map.put("status", true);
		} catch (Exception e) {
			String msg = "注册失败";
			map.put("msg",msg);
		}
		return map;
	}

	/**
	 * 
	 * @Title: logout
	 * @Description: 退出登录
	 * @author stonewuu 2017年1月19日 下午3:36:40
	 *
	 * @param request
	 * @param currUser
	 * @return
	 */
	@RequestMapping(value = { "logout" }, method = RequestMethod.GET)
	public ModelAndView logout(HttpServletRequest request, User currUser) {
		Subject subject = SecurityUtils.getSubject();
		subject.logout();
		return new ModelAndView("redirect:/auth/login");
	}
}
