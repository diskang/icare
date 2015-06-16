package com.sjtu.icare.modules.wechat.rule;

import java.util.Map;

import com.sjtu.icare.common.config.Global;

import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpMessageHandler;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.WxMpXmlOutMessage;
import me.chanjar.weixin.mp.bean.WxMpXmlOutNewsMessage;

public class GeroMessageHandler implements WxMpMessageHandler{
//	private static Logger logger = Logger.getLogger(PlatformMessageHandler.class);
	
//	@Autowired private IElderRelativeRelationshipService elderRelativeRelationshipService;
	
	@Override
	public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage,
			Map<String, Object> context, WxMpService wxMpService,
			WxSessionManager sessionManager) throws WxErrorException {
//		String appId = Global.getConfig("wechat.appId");
//		String openId = wxMessage.getFromUserName();
//		logger.debug(openId);
//		ElderRelativeRelationshipService.ElderRelativeRelationshipReturn relativeElders;
//		List<Elder>elders;
//		try{
//			//get elderInfos from openId
//			relativeElders = elderRelativeRelationshipService
//					.getElderRelativeRelationshipsByWechatId(openId);
//			if(relativeElders==null)throw new Exception();
//			String status = (String)relativeElders.getStatus();	
//			if(CommonConstants.SUBSCRIBED_WITHOUT_RELATIONSHIP_BINDING.equals(status)){
//				throw new Exception();
//			}else if(CommonConstants.SUBSCRIBED_WITH_RELATIONSHIP_BINDING.equals(status)){
//				elders = relativeElders.getElders();
//			}else{
//				throw new Exception();
//			}
//		}catch(Exception e){
//			e.printStackTrace();
//			logger.error("no elders bind");
//			return WxMpXmlOutMessage.TEXT()
//					.content(getUnbindPrompt(wxMpService))
//					.fromUser(wxMessage.getToUserName())
//					.toUser(wxMessage.getFromUserName())
//					.build();
//		}
//		for(Elder e:elders){
//			e.get elder's distinct gero ids
//			get notification according to the gero id
//		}
		
		//TODO should not set fixed, return article by given gero id
		WxMpXmlOutNewsMessage.Item item = new WxMpXmlOutNewsMessage.Item();
		item.setDescription("黄浦区老年公寓简介");
		item.setPicUrl("https://mmbiz.qlogo.cn/mmbiz/yOzUI5WmzvuCseL1GO1SYx8cmbu2bes1mSruRY1XOWAXXQM5u1v5mQ2z5JMcicltmMTkpVOQ7SJjolueT8Xxm1w/0?wx_fmt=jpeg");
		item.setTitle("黄浦区老年公寓");
		item.setUrl("http://mp.weixin.qq.com/s?__biz=MjM5MzUyMzU2Mg==&mid=219509330&idx=1&sn=66e1e6970adc6b611051c9467920ad19#rd");
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

	@SuppressWarnings("unused")
	private String getUnbindPrompt(WxMpService wxMpService){
		String host = Global.getConfig("api.host");
		String RedirectUrl = wxMpService.oauth2buildAuthorizationUrl(host+"/wechat/register", "snsapi_base", "1");    
		
		String hint = "您还未绑定任何老人，请点击"
				+ "<a href=\""+RedirectUrl+"\">这里</a>"
				+ " 绑定";
		return hint;
	}
}
