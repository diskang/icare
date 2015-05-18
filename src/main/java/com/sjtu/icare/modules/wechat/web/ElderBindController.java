package com.sjtu.icare.modules.wechat.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpServiceImpl;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sjtu.icare.common.persistence.Page;
import com.sjtu.icare.common.security.Digests;
import com.sjtu.icare.common.utils.Encodes;
import com.sjtu.icare.common.web.BaseController;
import com.sjtu.icare.modules.gero.entity.GeroEntity;
import com.sjtu.icare.modules.gero.service.IGeroService;
import com.sjtu.icare.modules.sys.entity.User;
import com.sjtu.icare.modules.sys.service.SystemService;
import com.sjtu.icare.modules.wechat.service.IElderRelativeRelationshipService;

@Controller
public class ElderBindController extends BaseController{
	@Autowired private WxMpServiceImpl wxMpService;
	@Autowired private IElderRelativeRelationshipService elderRelativeRelationshipService;
	@Autowired private SystemService systemService;
	@Autowired private IGeroService geroService;
	private static Logger logger = Logger.getLogger(ElderBindController.class);
	
	/*
	 * param = wechatId XOR code
	 * 1.  with wechatId after register successfully
	 * 2.  with code from clicking the menu button 
	 * */
	@RequestMapping({"/wechat/bind"})
	public String register(@RequestParam(value="wechat_id",required=false) String wechatId, 
			@RequestParam(value="code",required=false) String code,
			HttpServletRequest request, HttpServletResponse response, Model model) {
		String openId="";
		User user= null;
		String digest ="";
		List<GeroEntity> geroEntities = null;
		
		if(code==null||code.isEmpty()){
			if(wechatId==null||wechatId.isEmpty()){
				logger.error("no user information given");
				return "error/403";
			}else{
				openId = wechatId;
			}
		}else{
			try {
				WxMpOAuth2AccessToken wxMpOAuth2AccessToken = wxMpService.oauth2getAccessToken(code);
				openId = wxMpOAuth2AccessToken.getOpenId();
			} catch (WxErrorException e) {
				logger.error(e.getMessage());
				e.printStackTrace();
				return "error/403";
			}
			if(openId==null || openId.isEmpty()){
				logger.error("get AccessToken failed or code invalid, no openId found");
				return "error/403";
			}
		}
		
		user = systemService.getUserByWechatId(openId);
		if(user==null){//wechatId not found in db table, direct to register page
			model.addAttribute("wechatId",openId);
			return "module/wechat/register";
		}
		digest = Encodes.encodeHex(Digests.sha1(user.getPassword().getBytes()));
		GeroEntity requestGeroEntity = new GeroEntity();
		
		Page<GeroEntity> pages = new Page<GeroEntity>(1,20);//page,rows
		requestGeroEntity.setPage(pages);
		
		geroEntities = geroService.getGeros(requestGeroEntity);
		//now get digest`
		
		model.addAttribute("wechatId",openId);
		model.addAttribute("digest",digest);
		model.addAttribute("user",user);//remove user password TODO
		//Integer relativeUserId = user.getId();
		model.addAttribute("geroList",geroEntities);
        //TODO model.addAttr( relative's elder already binded)  ??? not used now

		//in jsp ,two steps: 1.get elder candidates and 2.choose one to submit
		return "module/wechat/bind";
	}
	
}
