package com.sjtu.icare.modules.wechat.rule;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpMessageHandler;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.WxMpServiceImpl;
import me.chanjar.weixin.mp.bean.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.WxMpTemplateMessage;
import me.chanjar.weixin.mp.bean.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.WxMpXmlOutMessage;

import com.sjtu.icare.modules.elder.service.IElderHealthDataService;
/*
 * not used now. template can not display multi-messages
 * if using this template handler, you should change menu to click type, add corresponding rule in router
 * if not use it, undo above all
 * see view type HealthReportHandler
 * */
public class HealthReportHandler implements WxMpMessageHandler{
	@Autowired WxMpServiceImpl wxMpService;
	
	@Autowired
	private IElderHealthDataService elderHealthDataService;
	
	@Override
	public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage,
			Map<String, Object> context, WxMpService wxMpService,
			WxSessionManager sessionManager) throws WxErrorException {
		
		
		WxMpTemplateMessage templateMessage = new WxMpTemplateMessage();
		String openId = wxMessage.getFromUserName();
		templateMessage.setToUser(openId);
		
		templateMessage.setTemplateId("sfEubp4g7lEtSPb-LiZ8_TaJkDN9Xc9mis2VER5LZAE");
		String detailUrl = 
				"http://202.120.38.227/wechat/healthreport/detail?open_id="+openId;
		templateMessage.setUrl(detailUrl);
		templateMessage.setTopColor("#FF0000");
		templateMessage.getDatas().add(new WxMpTemplateData("title", "xxx", "#173177"));
		templateMessage.getDatas().add(new WxMpTemplateData("bp", "120/70","#173177"));
		templateMessage.getDatas().add(new WxMpTemplateData("tp", "37", "#173177"));
		templateMessage.getDatas().add(new WxMpTemplateData("hr", "66", "#173177"));		
		templateMessage.getDatas().add(new WxMpTemplateData("remark", "非常健康", "#173177"));
		wxMpService.templateSend(templateMessage);
		return null;
	}}
