package com.sjtu.icare.modules.wechat.rule;

import java.util.Map;

import org.apache.log4j.Logger;

import com.sjtu.icare.common.config.Global;

import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpMessageHandler;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.WxMpXmlOutMessage;
import me.chanjar.weixin.mp.bean.WxMpXmlOutTextMessage;

public class SubscribeHandler implements WxMpMessageHandler{
	private static Logger logger = Logger.getLogger(SubscribeHandler.class);
	@Override
	public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage,
			Map<String, Object> context, WxMpService wxMpService,
			WxSessionManager sessionManager) throws WxErrorException {
		String appId = Global.getConfig("wechat.appId");
		logger.debug(appId);
		String welcomeMsg = "welcome to us. please click "
				+ "<a href=\"https://open.weixin.qq.com/connect/oauth2/authorize"
				+ "?appid="+appId
				+ "&redirect_uri=http%3A%2F%2F202.12.38.227%2Fwechat%2Ftoken"
				+ "&response_type=code"
				+ "&scope=snsapi_userinfo"
				+ "&state=STATE"
				+ "#wechat_redirect\">here</a>"
				+ " to register";
		WxMpXmlOutTextMessage m
        = WxMpXmlOutMessage.TEXT().content(welcomeMsg).fromUser(wxMessage.getToUserName())
        .toUser(wxMessage.getFromUserName()).build();
        return m;
	}

}
