package com.stonewuu.controller;

import org.springframework.web.servlet.ModelAndView;

public class BaseController {
	public ModelAndView getModel(){
		ModelAndView view = new ModelAndView();
		return view;
	}
}
