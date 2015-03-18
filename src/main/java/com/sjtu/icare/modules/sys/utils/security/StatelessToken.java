package com.sjtu.icare.modules.sys.utils.security;

import java.util.Map;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * 无状态token令牌类
 * @author jty
 * @version 2015-03-06
 */
public class StatelessToken implements AuthenticationToken {  
	
	private static final long serialVersionUID = 1L;
	private String username;  //用户名
    private Map<String, ?> params;  //请求参数
    private String clientDigest;  //摘要
    //省略部分代码  
    public StatelessToken(String username, Map<String, ?> params,
			String clientDigest) {
		super();
		this.username = username;
		this.params = params;
		this.clientDigest = clientDigest;
	}    
    
    public Object getPrincipal() {  return username;}  
    
	public Object getCredentials() {  return clientDigest;}
	
    public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public Map<String, ?> getParams() {
		return params;
	}
	public void setParams(Map<String, ?> params) {
		this.params = params;
	}
	public String getClientDigest() {
		return clientDigest;
	}
	public void setClientDigest(String clientDigest) {
		this.clientDigest = clientDigest;
	}  
} 
