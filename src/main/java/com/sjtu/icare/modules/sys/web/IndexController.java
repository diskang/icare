package com.sjtu.icare.modules.sys.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.sjtu.icare.common.web.BaseController;

@Controller
public class IndexController  extends BaseController{
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String getIndex() {
		logger.debug("index");
		return "index";
	}
}
