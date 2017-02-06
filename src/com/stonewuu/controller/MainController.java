package com.stonewuu.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/")
public class MainController extends BaseController {

	@RequestMapping(value = { "","index" }, method = RequestMethod.GET)
	public ModelAndView index(HttpServletRequest request) {
		ModelAndView view = super.getModel(request);
		view.setViewName("/index");
		return view;
	}

}
