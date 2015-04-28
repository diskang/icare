package com.sjtu.icare.modules.wechat.rule;

import java.util.Map;

import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpMessageHandler;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.WxMpXmlOutMessage;
import me.chanjar.weixin.mp.bean.WxMpXmlOutTextMessage;

public class SubscribeHandler implements WxMpMessageHandler{

	@Override
	public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage,
			Map<String, Object> context, WxMpService wxMpService,
			WxSessionManager sessionManager) throws WxErrorException {
		WxMpXmlOutTextMessage m
        = WxMpXmlOutMessage.TEXT().content("welcome to us. please click to register").fromUser(wxMessage.getToUserName())
        .toUser(wxMessage.getFromUserName()).build();
        return m;
	}

}
