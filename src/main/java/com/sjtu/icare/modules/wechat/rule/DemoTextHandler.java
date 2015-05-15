package com.sjtu.icare.modules.wechat.rule;

import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpMessageHandler;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.WxMpXmlOutMessage;
import me.chanjar.weixin.mp.bean.WxMpXmlOutTextMessage;

import java.util.Map;

/**
 * Created by qianjia on 15/1/22.
 */
public class DemoTextHandler implements WxMpMessageHandler {
  @Override
  public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage, Map<String, Object> context,
      WxMpService wxMpService, WxSessionManager sessionManager) {
    WxMpXmlOutTextMessage m
        = WxMpXmlOutMessage.TEXT().content("测试消息").fromUser(wxMessage.getToUserName())
        .toUser(wxMessage.getFromUserName()).build();
    return m;
  }

}
