package com.sjtu.icare.modules.wechat.rule;

import java.util.Map;

import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpMessageHandler;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.WxMpXmlOutMessage;
import me.chanjar.weixin.mp.bean.WxMpXmlOutNewsMessage;

import org.apache.log4j.Logger;

import com.sjtu.icare.common.config.Global;

public class PlatformMessageHandler implements WxMpMessageHandler{
	private static Logger logger = Logger.getLogger(PlatformMessageHandler.class);
	@Override
	public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage,
			Map<String, Object> context, WxMpService wxMpService,
			WxSessionManager sessionManager) throws WxErrorException {
		String appId = Global.getConfig("wechat.appId");
		logger.debug(appId);
		WxMpXmlOutNewsMessage.Item item = new WxMpXmlOutNewsMessage.Item();
		item.setDescription("description");
		item.setPicUrl("http://pic1.zhimg.com/e076d5d7ee095c31e10ee9ceea9229de_b.jpg");
		item.setTitle("title");
		item.setUrl("http://mp.weixin.qq.com/s?__biz=MzA5NTc0Njg3NQ==&mid=204958618&idx=1&sn=53eac379a5284ae7b76a8b8dde1ba1dd#rd");
		WxMpXmlOutNewsMessage.Item item2 = new WxMpXmlOutNewsMessage.Item();
		item2.setDescription("description");
		item2.setPicUrl("http://pic1.zhimg.com/e076d5d7ee095c31e10ee9ceea9229de_b.jpg");
		item2.setTitle("title");
		item2.setUrl("http://mp.weixin.qq.com/s?__biz=MzA5NTc0Njg3NQ==&mid=204958618&idx=2&sn=7a02dfc1dc16eb745a508855c354fd80#rd");
		WxMpXmlOutNewsMessage m=
		WxMpXmlOutMessage.NEWS()
		  .addArticle(item)
		  .addArticle(item2)
		  .fromUser(wxMessage.getToUserName())
		  .toUser(wxMessage.getFromUserName())
		  .build();
		
        return m;
	}

}
