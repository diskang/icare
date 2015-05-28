package com.sjtu.icare.modules.wechat.rule;

import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.sjtu.icare.common.config.Global;

import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpMessageHandler;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.WxMpServiceImpl;
import me.chanjar.weixin.mp.bean.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.WxMpXmlOutMessage;
import me.chanjar.weixin.mp.bean.WxMpXmlOutTextMessage;

public class SubscribeHandler implements WxMpMessageHandler{
	private static Logger logger = Logger.getLogger(SubscribeHandler.class);
	@Autowired WxMpServiceImpl wxMpService;
	
	@Override
	public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage,
			Map<String, Object> context, WxMpService wxMpService,
			WxSessionManager sessionManager) throws WxErrorException {
		String appId = Global.getConfig("wechat.appId");
		logger.debug(appId);
		String RedirectUrl = wxMpService.oauth2buildAuthorizationUrl("http://202.120.38.227/wechat/register", "snsapi_base", "1");    
			
		String welcomeMsg = "welcome to us. please click "
				+ "<a href=\""+RedirectUrl+"\">here</a>"
				+ " to register";
		WxMpXmlOutTextMessage m
        = WxMpXmlOutMessage.TEXT().content(welcomeMsg).fromUser(wxMessage.getToUserName())
        .toUser(wxMessage.getFromUserName()).build();
        return m;
	}

}
