package com.stonewuu.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.web.servlet.ModelAndView;

public class BaseController {
	public ModelAndView getModel(HttpServletRequest request){
		ModelAndView view = new ModelAndView();
		String path = request.getContextPath();
		String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
		view.addObject("path",path);
		view.addObject("basePath",basePath);
		return view;
	}
	
	public String getCurrentUserName(){
		Subject subject = SecurityUtils.getSubject();
		return (String) subject.getPrincipal();
	}
}
