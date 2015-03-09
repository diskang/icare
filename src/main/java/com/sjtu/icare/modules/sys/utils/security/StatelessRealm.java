package com.sjtu.icare.modules.sys.utils.security;

import java.util.List;

import org.apache.log4j.Logger;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.sjtu.icare.common.security.HmacSHA256Utils;
import com.sjtu.icare.common.utils.SpringContextHolder;
import com.sjtu.icare.common.utils.StringUtils;
import com.sjtu.icare.common.web.rest.RestException;
import com.sjtu.icare.modules.sys.entity.Privilege;
import com.sjtu.icare.modules.sys.entity.User;
import com.sjtu.icare.modules.sys.service.SystemService;
import com.sjtu.icare.modules.sys.utils.UserUtils;
import com.sjtu.icare.modules.sys.web.TestController;

/**
 * 无状态认证实现类
 * @author jty
 * @version 2015-03-06
 */
@Service
@DependsOn({"userMapper"})
public class StatelessRealm extends AuthorizingRealm {
	
	private static final Logger logger = Logger.getLogger(StatelessRealm.class);
	private SystemService systemService;
	
    public boolean supports(AuthenticationToken token) {
        //仅支持StatelessToken类型的Token
        return token instanceof StatelessToken;
    }
       
    /**
     * 认证回调函数, 登录时调用
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        StatelessToken statelessToken = (StatelessToken) token;
        String username = statelessToken.getUsername();
        logger.debug(username);
        String key = getKey(username);//根据用户名获取密钥（和客户端的一样）
        //在服务器端生成客户端参数消息摘要
        String serverDigest = HmacSHA256Utils.digest(key, statelessToken.getParams());
        logger.debug(serverDigest);
        //然后进行客户端消息摘要和服务器端消息摘要的匹配
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(
                username,
                serverDigest,
                getName());
        return info;
    }
    
    /**
     * 授权查询回调函数, 进行鉴权但缓存中无用户的授权信息时调用
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        //根据用户名查找角色，请根据需求实现
        String username = (String) principals.getPrimaryPrincipal();
        User user = getSystemService().getUserByUsername(username);
        logger.debug("stateless_user:"+user.getUsername());
        if (user.getUsername() != null) {
        	logger.debug("user getted");
//            UserUtils.putCache("user", user);
            SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
            List<Privilege> list = UserUtils.getPrivilegeList(user);
            for (Privilege privilege : list){
                if (StringUtils.isNotBlank(privilege.getPermission())){
                    // 添加基于Permission的权限信息
                    for (String permission : StringUtils.split(privilege.getPermission(),",")){
                    	logger.debug("stateless_permission:"+permission);
                        info.addStringPermission(permission);
                    }
                }
            }
            return info;
        } else {
            return null;
        }
    }
    
    private String getKey(String username) {//得到密钥，此处硬编码一个
    	SystemService systemService = getSystemService();
    	User user = systemService.getUserByUsername(username);
        return user.getPassword();
    }
    
    /**
     * 获取系统业务对象
     */
    public SystemService getSystemService() {
        if (systemService == null){
            systemService = SpringContextHolder.getBean(SystemService.class);
        }
        return systemService;
    }
    
    /**
     * {@inheritDoc}
     * 重写函数，如果不是web app登录，则用无状态授权
     */
    @Override
    public boolean isPermitted(PrincipalCollection principals, String permission) throws RestException{
        
    	logger.debug("statelss auth");
    	Boolean result = false;
    	try {
    		result = super.isPermitted(principals, permission);
		} catch (Exception e) {
			// TODO: handle exception
			logger.debug("exception");
		}
        return result;
    }
}
