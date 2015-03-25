package com.sjtu.icare.modules.sys.web;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sjtu.icare.common.config.Global;
import com.sjtu.icare.common.web.BaseController;
import com.sjtu.icare.modules.sys.entity.User;
import com.sjtu.icare.modules.sys.utils.UserUtils;

/**
 * 测试类
 * @author garfieldjty
 *
 */
@Controller
public class AdminController extends BaseController{
	
	/**
	 * 管理登录
	 */
	@RequestMapping(value = "${adminPath}", method = RequestMethod.GET)
	@ResponseBody
//	@RequiresPermissions("/user/{uid}#GET")
//	@RequiresRoles("gero:1")
	public String login(HttpServletRequest request, HttpServletResponse response, Model model) {
		
		logger.debug("admin!");
//		SecurityUtils.getSubject().hasRole("gero:1");
			User user = UserUtils.getUser();
			response.addCookie(new Cookie("uid", user.getId()+""));
			response.addCookie(new Cookie("gid", user.getGeroId()+""));
			// 如果已经登录，则跳转到管理首页
			if(user.getUsername() != null){
				if (SecurityUtils.getSubject().isPermitted("admin")){
					logger.debug("admin");
					return "redirect:"+Global.getAdminPath();
				}
			}
//		}
		return "module/sys/sysLogin";
	}
}