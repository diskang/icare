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
		String host = Global.getConfig("api.host");
		logger.debug(appId);
		String registerUrl = host+"/wechat/register";
		String RedirectUrl = wxMpService.oauth2buildAuthorizationUrl(registerUrl, "snsapi_base", "1");
		String welcomeMsg = "欢迎关注Housecare老伴公众账号，请点击 "
				+ "<a href=\""+RedirectUrl+"\">这里</a>"
				+ " 注册";
		WxMpXmlOutTextMessage m
        = WxMpXmlOutMessage.TEXT().content(welcomeMsg).fromUser(wxMessage.getToUserName())
        .toUser(wxMessage.getFromUserName()).build();
        return m;
	}

}
