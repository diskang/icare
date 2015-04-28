package com.sjtu.icare.modules.wechat.rule;

import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpMessageHandler;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.WxMpXmlOutMessage;

import java.util.Map;

import org.apache.log4j.Logger;

/**
 * Created by qianjia on 15/1/22.
 */
public class DemoLogHandler implements WxMpMessageHandler {
	private static Logger logger = Logger.getLogger(DemoLogHandler.class);
  @Override
  public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage, Map<String, Object> context, WxMpService wxMpService,
      WxSessionManager sessionManager) {
	  logger.warn(wxMessage.toString());
    return null;
  }
}
