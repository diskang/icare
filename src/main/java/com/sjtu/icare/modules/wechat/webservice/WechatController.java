package com.sjtu.icare.modules.wechat.webservice;

import me.chanjar.weixin.mp.api.WxMpMessageRouter;
import me.chanjar.weixin.mp.api.WxMpServiceImpl;
import me.chanjar.weixin.mp.bean.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.WxMpXmlOutMessage;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/wechat")
public class WechatController {
	private static Logger logger = Logger.getLogger(WechatController.class);
    @Autowired WxMpServiceImpl wxMpService;
    @Autowired WxMpMessageRouter wxMpMessageRouter;

	/*
	 * 1. 将token、timestamp、nonce三个参数进行字典序排序
     * 2. 将三个参数字符串拼接成一个字符串进行sha1加密
     * 3. 开发者获得加密后的字符串可与signature对比，标识该请求来源于微信
	 * */
    @ResponseBody
    @RequestMapping( method = RequestMethod.GET)
    public String wechatServerVerification(@RequestParam("signature") String signature,
            @RequestParam("timestamp") String timestamp,
            @RequestParam("nonce") String nonce,
            @RequestParam("echostr") String echostr){
		//TODO
    	return echostr;
    }
	
	@RequestMapping( method = RequestMethod.POST)
	@ResponseBody
	public String messageRouter(
//			@RequestParam("signature") String signature,
//    		@RequestParam("timestamp") String timestamp,
//    		@RequestParam("nonce") String nonce,
//    		@RequestParam("encrypt_type") String encryptType,
    		@RequestBody String msgFromBody){
//		if (!wxMpService.checkSignature(timestamp, nonce, signature)) {
//			logger.warn("wechat meaage not raw type");
//			return null;
//		}
//		if ("raw".equals(encryptType)) {
		      // 明文传输的消息
			logger.warn(msgFromBody);
			//TODO error handler
		      WxMpXmlMessage inMessage = WxMpXmlMessage.fromXml(msgFromBody);
		      WxMpXmlOutMessage outMessage = wxMpMessageRouter.route(inMessage);
		      if (outMessage != null) {
		          return outMessage.toXml();
		      }      
//        }
        return "<xml></xml>";
	}
}
