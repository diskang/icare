package com.sjtu.icare.modules.wechat.utils;

import org.apache.log4j.Logger;

import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.mp.api.WxMpMessageHandler;
import me.chanjar.weixin.mp.api.WxMpMessageRouter;

import com.sjtu.icare.modules.wechat.rule.DemoLogHandler;
import com.sjtu.icare.modules.wechat.rule.DemoTextHandler;
import com.sjtu.icare.modules.wechat.rule.GeroMessageHandler;
import com.sjtu.icare.modules.wechat.rule.PlatformMessageHandler;
import com.sjtu.icare.modules.wechat.rule.SubscribeHandler;

public class RouterConfiguration {
	private static Logger logger = Logger.getLogger(RouterConfiguration.class);
	
    public RouterConfiguration(WxMpMessageRouter wxMpMessageRouter) {
    	WxMpMessageHandler logHandler = new DemoLogHandler();
    	WxMpMessageHandler textHandler = new DemoTextHandler();
    	WxMpMessageHandler subscribeHandler = new SubscribeHandler();
    	WxMpMessageHandler platformMessageHandler = new PlatformMessageHandler();
    	WxMpMessageHandler geroMessageHandler = new GeroMessageHandler();
    	wxMpMessageRouter.rule().handler(logHandler).next()
    	.rule().async(false).content("test").handler(textHandler).end()
    	.rule().async(false).msgType(WxConsts.XML_MSG_EVENT)
    	       .event(WxConsts.EVT_SUBSCRIBE).handler(subscribeHandler).end()
    	.rule().async(false).msgType(WxConsts.XML_MSG_EVENT)
    	       .event(WxConsts.EVT_CLICK).eventKey("sys_notice").handler(platformMessageHandler).end()
    	.rule().async(false).msgType(WxConsts.XML_MSG_EVENT)
    	       .event(WxConsts.EVT_CLICK).eventKey("gero_notice").handler(geroMessageHandler).end()
    	       ;
    	logger.warn("router handler configured");
	}
}
