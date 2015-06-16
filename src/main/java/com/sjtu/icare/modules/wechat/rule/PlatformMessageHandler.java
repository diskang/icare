package com.sjtu.icare.modules.wechat.rule;

import java.util.Map;

import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpMessageHandler;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.WxMpXmlOutMessage;
import me.chanjar.weixin.mp.bean.WxMpXmlOutNewsMessage;

import com.sjtu.icare.common.config.Global;

public class PlatformMessageHandler implements WxMpMessageHandler{
//	private static Logger logger = Logger.getLogger(PlatformMessageHandler.class);
	
//	@Autowired private IElderRelativeRelationshipService elderRelativeRelationshipService;
	
	@Override
	public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage,
			Map<String, Object> context, WxMpService wxMpService,
			WxSessionManager sessionManager) throws WxErrorException {
		String host = Global.getConfig("api.host");
		WxMpXmlOutNewsMessage.Item item = new WxMpXmlOutNewsMessage.Item();
		item.setDescription("老伴养老平台");
		item.setPicUrl("https://mmbiz.qlogo.cn/mmbiz/yOzUI5WmzvuCseL1GO1SYx8cmbu2bes1FwkvGVgHaCbaPovxMWrFUOV2V5TFiaIEDPZUbTAGkZVgKd0Hr7xvvcg/0?wx_fmt=jpeg");
		item.setTitle("老伴养老平台");
		item.setUrl(host);
//		WxMpXmlOutNewsMessage.Item item2 = new WxMpXmlOutNewsMessage.Item();
//		item2.setDescription("description");
//		item2.setPicUrl("http://pic1.zhimg.com/e076d5d7ee095c31e10ee9ceea9229de_b.jpg");
//		item2.setTitle("title");
//		item2.setUrl("http://mp.weixin.qq.com/s?__biz=MzA5NTc0Njg3NQ==&mid=204958618&idx=2&sn=7a02dfc1dc16eb745a508855c354fd80#rd");
		WxMpXmlOutNewsMessage m=
		WxMpXmlOutMessage.NEWS()
		  .addArticle(item)
//		  .addArticle(item2)
		  .fromUser(wxMessage.getToUserName())
		  .toUser(wxMessage.getFromUserName())
		  .build();
        return m;
	}

}
