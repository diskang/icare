package com.sjtu.icare.common.web;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import com.alibaba.fastjson.JSONObject;

/**
 * Web Application 与 Web Service 错误的分别处理类
 * @author jty
 * @version 2015-03-08
 */
public class CustomSimpleMappingExceptionResolver extends SimpleMappingExceptionResolver{
    @Override
    protected ModelAndView doResolveException(HttpServletRequest request,
            HttpServletResponse response,
            Object handler,
            Exception ex) {

        // Expose ModelAndView for chosen error view.
        String viewName = determineViewName(ex, request);
        if (viewName != null) {//JSP格式返回
            if(!(request.getHeader("accept").indexOf("application/json")>-1)){//如果不是异步请求
                // Apply HTTP status code for error views, if specified.
                // Only apply it if we're processing a top-level request.
                Integer statusCode = Integer.parseInt(viewName.split("/")[1]);
                if (statusCode != null) {
                    applyStatusCodeIfPossible(request, response, statusCode);
                    return getModelAndView(viewName, ex, request);
                }
            }else{//JSON格式返回
                JSONObject errorJson = new JSONObject();
                if(ex.getClass().equals(UnauthorizedException.class)){
                    errorJson.put("error", "Unauthorized");
                    errorJson.put("errno", HttpStatus.UNAUTHORIZED);
                }
                
                try {
                	response.setStatus(HttpStatus.UNAUTHORIZED.value());
                    response.setContentType("application/json");
                    response.getWriter().write(errorJson.toJSONString());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return new ModelAndView();
            }
            return null;
        }
        else {
            return null;
        }
    }
}
