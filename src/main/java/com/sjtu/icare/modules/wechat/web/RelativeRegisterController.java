package com.sjtu.icare.modules.wechat.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sjtu.icare.common.web.BaseController;
import com.sjtu.icare.modules.sys.entity.User;
import com.sjtu.icare.modules.sys.utils.UserUtils;
@Controller
public class RelativeRegisterController extends BaseController{
	@Autowired WxMpService wxMpService;
	private static Logger logger = Logger.getLogger(RelativeRegisterController.class);
	
	@RequestMapping({"/wechat/register"})
	public String register(@RequestParam("code") String code, 
			@RequestParam(value="code",required=false) String state,
			HttpServletRequest request, HttpServletResponse response, Model model) {
		
		if(code==null||code.isEmpty()){
			logger.error("require param code, but not given");
			return "error/403";
		}
		String openId="";
		try {
			WxMpOAuth2AccessToken wxMpOAuth2AccessToken = wxMpService.oauth2getAccessToken(code);
			openId = wxMpOAuth2AccessToken.getOpenId();
			if(openId==null || openId.isEmpty()){
				logger.error("get AccessToken failed or code invalid, no openId found");
				return "error/403";
			}
		} catch (WxErrorException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			return "error/403";
		}
		//check openId
		model.addAttribute("wechatId", openId);
		
		return "module/sys/sysLogin";
	}
}
