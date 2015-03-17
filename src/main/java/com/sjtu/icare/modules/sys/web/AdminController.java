package com.sjtu.icare.modules.sys.web;

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
	public String login(HttpServletRequest request, HttpServletResponse response, Model model) {
		logger.debug("admin!");
			User user = UserUtils.getUser();
			// 如果已经登录，则跳转到管理首页
			if(user.getUsername() != null){
				logger.debug("admin");
				return "module/gero_management";
			}
//		}
		return "module/sys/sysLogin";
	}
}