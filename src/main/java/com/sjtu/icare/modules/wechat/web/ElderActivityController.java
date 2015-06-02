package com.sjtu.icare.modules.wechat.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.sjtu.icare.common.web.BaseController;
import com.sjtu.icare.common.web.rest.MediaTypes;

@Controller
@RequestMapping({"/wechat/activity"})
public class ElderActivityController extends BaseController{
	
	@Autowired WxMpService wxMpService;
	
	@RequestMapping(method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
	public String getMenu(@RequestParam(value="code",required=false) String code,
			@RequestParam(value="wechat_id",required=false) String wechatId, 
			HttpServletRequest request, HttpServletResponse response, Model model){
		
		String openId ="";
		if(code==null||code.isEmpty()){
			if(wechatId==null||wechatId.isEmpty()){
				logger.error("no user information given");
				return "error/403";//TODO add a 400 page
			}else{
				openId = wechatId;
			}
		}else{
			//get openId by code
			try {
				WxMpOAuth2AccessToken wxMpOAuth2AccessToken = wxMpService.oauth2getAccessToken(code);
				openId = wxMpOAuth2AccessToken.getOpenId();
			} catch (WxErrorException e) {
				return "error/404";
			}
			if(openId==null || openId.isEmpty()){
				logger.error("get AccessToken failed or code invalid, no openId found");
				return "error/403";
			}
		}
		return "module/wechat/elderActivity";
	}
}
