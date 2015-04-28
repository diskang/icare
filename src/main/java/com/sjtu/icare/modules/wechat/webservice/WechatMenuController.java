package com.sjtu.icare.modules.wechat.webservice;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import me.chanjar.weixin.common.bean.WxMenu;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;
import me.chanjar.weixin.mp.api.WxMpMessageRouter;
import me.chanjar.weixin.mp.api.WxMpServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sjtu.icare.common.utils.BasicReturnedJson;
import com.sjtu.icare.common.web.rest.BasicController;
import com.sjtu.icare.common.web.rest.MediaTypes;
import com.sjtu.icare.common.web.rest.RestException;

@RestController
@RequestMapping({"${api.web}/wechat/menu","${api.service}/wechat/menu"})
public class WechatMenuController extends BasicController{

	@Autowired WxMpInMemoryConfigStorage wxMpConfigStorage;
	@Autowired WxMpServiceImpl wxMpService;
	@Autowired WxMpMessageRouter wxMpMessageRouter;
	
	@RequestMapping(method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
	public Map<String, Object> getMenu(HttpServletRequest request){
//		checkApi(request);
		BasicReturnedJson result = new BasicReturnedJson();
		try {
			WxMenu wxMenu = wxMpService.menuGet();
			result.addEntity(wxMenu);
			
		} catch (WxErrorException e) {
			throw new RestException(HttpStatus.NOT_FOUND, "cannot get wechat menu");
		}
		return result.getMap();
	}
	/*
	 * sample:
	 *  要用 http://mp.weixin.qq.com/wiki/16/ff9b7b85220e1396ffa16794a9d95adc.html 格式
     * 相比 http://mp.weixin.qq.com/wiki/13/43de8269be54a0a6f64413e4dfa94f39.html 的格式，外层多套了一个menu
{"menu":{
     "button":[
     {	
          "type":"click",
          "name":"今日歌曲",
          "key":"V1001_TODAY_MUSIC"
      },
      {
           "name":"菜单",
           "sub_button":[
           {	
               "type":"view",
               "name":"搜索",
               "url":"http://www.soso.com/"
            },
            {
               "type":"view",
               "name":"视频",
               "url":"http://v.qq.com/"
            },
            {
               "type":"click",
               "name":"赞一下我们",
               "key":"V1001_GOOD"
            }]
       }]
 }}
	 * */
	@RequestMapping(method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
	public Map<String, Object> createMenu(HttpServletRequest request,
			@RequestBody String inJson){
//		checkApi(request);
		BasicReturnedJson result = new BasicReturnedJson();
		try {
			WxMenu wxMenu = WxMenu.fromJson(inJson);
			wxMpService.menuCreate(wxMenu);
			result.addEntity(wxMenu);
			
		} catch (WxErrorException e) {
			throw new RestException(HttpStatus.BAD_REQUEST, "cannot create wechat menu");
		} catch(Exception e2){
			e2.printStackTrace();
			throw new RestException(HttpStatus.INTERNAL_SERVER_ERROR, e2.getMessage());
		}
		return result.getMap();
	}
	
	@RequestMapping(method = RequestMethod.DELETE, produces = MediaTypes.JSON_UTF_8)
	public Map<String, Object> deleteMenu(HttpServletRequest request){
//		checkApi(request);
		BasicReturnedJson result = new BasicReturnedJson();
		try {
			wxMpService.menuDelete();
		} catch (WxErrorException e) {
			throw new RestException(HttpStatus.SERVICE_UNAVAILABLE, "cannot delete wechat menu");
		}
		return result.getMap();
	}
}
