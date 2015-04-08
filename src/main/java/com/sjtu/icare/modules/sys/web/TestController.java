//package com.sjtu.icare.modules.sys.web;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.apache.log4j.Logger;
//import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import com.sjtu.icare.common.config.Global;
//import com.sjtu.icare.modules.sys.entity.User;
//
///**
// * 测试类
// * @author KangShiyong
// *
// */
//@Controller
//@RequestMapping("/test")
//public class TestController {
//    private static final Logger logger = Logger.getLogger(TestController.class);
//    /*simple example*/
//    @RequestMapping("/jsp")
//    public String jsp() {
//        return "test/testJsp";
//    }
//    
//    /*param passing*/
//    @RequestMapping("/index")
//    public String index(int param) {
//        System.out.println("param:" + param);
//        return "index";
//    }
//
//    /*send HTTP body instead of jsp*/
//    @RequestMapping("ajax")
//    @ResponseBody
//    public Object ajax(HttpServletRequest request){  
//        List<String> list=new ArrayList<String>();  
//        list.add("电视");  
//        list.add("洗衣机");  
//        list.add("冰箱");  
//        list.add("电脑");  
//        list.add("汽车");  
//        list.add("空调");  
//        list.add("自行车");  
//        list.add("饮水机");  
//        list.add("热水器");  
//        return list;  
//    }  
//    
//    /*Logger usage*/
//    @RequestMapping("/log")
//    public String log(@RequestParam(defaultValue="default",required=false)String id){
//        logger.debug(id);// see console
//        return "index";
//    }
//    
//    /*POST请求既有RequestBody,又有RequestParam的情况*/
//    @RequestMapping(value = "/post", method = RequestMethod.POST)
//    public String login(User user, @RequestParam String attach,HttpServletRequest request, HttpServletResponse response, Model model) {
//       System.out.println(user.getUsername());
//       System.out.println(attach);
//        return "index";
//    }
//    
//}