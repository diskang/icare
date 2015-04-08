//package com.sjtu.icare.modules.sys.web;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.validation.BindingResult;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//
//import com.sjtu.icare.common.web.BaseController;
//import com.sjtu.icare.modules.sys.entity.Gero;
//import com.sjtu.icare.modules.sys.service.GeroTestService;
//
///**
// * 测试类
// * @author KangShiyong
// *
// */
//@Controller
//@RequestMapping("/test/gero")
//public class GeroController extends BaseController{
//	@Autowired
//	private GeroTestService geroService;
//
//	@RequestMapping(method = RequestMethod.POST)
//	public String insertGero( Gero gero, BindingResult result, Model model){
//		if (beanValidator(model, gero)){
//			geroService.insertGero(gero);
//			model.addAttribute("id",gero.getId());
//	        model.addAttribute("name",gero.getName());
//	        return "test/demoGero";
//		}
//	    else {
//	        return "error/500";
//	    }
//	}
//
//	@RequestMapping(value="/{id}",method = RequestMethod.GET)
//	public String getGero(@PathVariable int id, Model model){
//		Gero gero= geroService.getGero(id);
//		model.addAttribute("id",gero.getId());
//		model.addAttribute("name",gero.getName());
//		return "test/demoGero";	
//	}
//}