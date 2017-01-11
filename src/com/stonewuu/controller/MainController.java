package com.stonewuu.controller;

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
import org.springframework.web.servlet.ModelAndView;

import com.stonewuu.entity.User;

@Controller
@RequestMapping(value = "/")
public class MainController extends BaseController {
	private static final Log log = LogFactory.getLog(MainController.class);

	@RequestMapping(value = { "index" }, method = RequestMethod.GET)
	public ModelAndView index() {
		ModelAndView view = getModel();
		view.setViewName("/index");
		return view;
	}

	@RequestMapping(value = { "login" }, method = RequestMethod.POST)
	public ModelAndView login(User currUser) {
		ModelAndView view = getModel();
		view.setViewName("/index");
		Subject subject = SecurityUtils.getSubject();
		UsernamePasswordToken token = new UsernamePasswordToken(currUser.getName(), currUser.getPassword());
		String msg = "";
		try {
			subject.login(token);
		} catch (IncorrectCredentialsException e) {
			msg = "登录密码错误. Password for account " + token.getPrincipal() + " was incorrect.";
			view.addObject("message", msg);
			log.warn(msg);
		} catch (ExcessiveAttemptsException e) {
			msg = "登录失败次数过多";
			view.addObject("message", msg);
			log.warn(msg);
		} catch (LockedAccountException e) {
			msg = "帐号已被锁定. The account for username " + token.getPrincipal() + " was locked.";
			view.addObject("message", msg);
			log.warn(msg);
		} catch (DisabledAccountException e) {
			msg = "帐号已被禁用. The account for username " + token.getPrincipal() + " was disabled.";
			view.addObject("message", msg);
			log.warn(msg);
		} catch (ExpiredCredentialsException e) {
			msg = "帐号已过期. the account for username " + token.getPrincipal() + "  was expired.";
			view.addObject("message", msg);
			log.warn(msg);
		} catch (UnknownAccountException e) {
			msg = "帐号不存在. There is no user with username of " + token.getPrincipal();
			view.addObject("message", msg);
			log.warn(msg);
		} catch (UnauthorizedException e) {
			msg = "您没有得到相应的授权！" + e.getMessage();
			view.addObject("message", msg);
			log.warn(msg);
		} catch (AuthorizationException e) {
			log.warn("登录失败错误信息:" + e.getCause().getMessage());
			token.clear();
			return new ModelAndView("redirect:/index?error=true");
		} catch (AuthenticationException e) {
			log.warn("登录失败错误信息:" + e);
			token.clear();
			return new ModelAndView("redirect:/index?error=true");
		}
		return view;
	}
}
