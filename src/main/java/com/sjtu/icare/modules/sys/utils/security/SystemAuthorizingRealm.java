package com.sjtu.icare.modules.sys.utils.security;


import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;

import com.sjtu.icare.common.utils.Encodes;
import com.sjtu.icare.common.utils.SpringContextHolder;
import com.sjtu.icare.common.utils.StringUtils;
import com.sjtu.icare.common.web.ValidateCodeServlet;
import com.sjtu.icare.modules.sys.entity.User;
import com.sjtu.icare.modules.sys.service.SystemService;
import com.sjtu.icare.modules.sys.utils.UserUtils;
import com.sjtu.icare.modules.sys.web.LoginController;
import com.sjtu.icare.modules.sys.entity.Privilege;

/**
 * 系统安全认证实现类
 * @author jty
 * @version 2015-03-07
 */
@Service
@DependsOn({"userMapper"})
public class SystemAuthorizingRealm extends AuthorizingRealm {

	private static final Logger logger = Logger.getLogger(SystemAuthorizingRealm.class);
    private SystemService systemService;

    /**
     * 认证回调函数, 登录时调用
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
        if (LoginController.isValidateCodeLogin(token.getUsername(), false, false)){
            // 判断验证码
            Session session = SecurityUtils.getSubject().getSession();
            String code = (String)session.getAttribute(ValidateCodeServlet.VALIDATE_CODE);
            if (token.getCaptcha() == null || !token.getCaptcha().toUpperCase().equals(code)){
                throw new CaptchaException("验证码错误.");
            }
        }
        SystemService systemService = getSystemService();
//        logger.debug("token pass"+token.getPassword().toString());
        User user = systemService.getUserByUsername(token.getUsername());
        logger.debug("user pass:"+user.getPassword().toString());
        if (user != null) {
            byte[] salt = Encodes.decodeHex(user.getPassword().substring(0,16));
            // 此处判断用户密码是否合法
            return new SimpleAuthenticationInfo(
            		new UserPrincipal(user), 
                    user.getPassword().substring(16), ByteSource.Util.bytes(salt), getName());
        } else {
            return null;
        }
    }

    /**
     * 授权查询回调函数, 进行鉴权但缓存中无用户的授权信息时调用
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
    	// logger.debug(getAvailablePrincipal(principals).getClass().toString());
        UserPrincipal principal = (UserPrincipal) getAvailablePrincipal(principals);
        // logger.debug("getpermisson");
        User user = getSystemService().getUserByUsername(principal.getUsername());
        if (user != null) {
            UserUtils.putCache("user", user);
            SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
            info.addRole("gero:"+user.getGeroId());
            logger.debug("role:"+"gero:"+user.getGeroId());
            List<Privilege> list = UserUtils.getPrivilegeList();
            for (Privilege privilege : list){
                if (StringUtils.isNotBlank(privilege.getPermission())){
                    // 添加基于Permission的权限信息
                	String permission = privilege.getPermission();
                	permission = permission.replace("{uid}", user.getId()+"");
                	permission = permission.replace("{gid}", user.getGeroId()+"");
                	permission = permission.replace("{sid}", user.getUserId()+"");
                	permission = permission.replace("{eid}", user.getUserId()+"");
                	permission = permission.replace("{cid}", user.getUserId()+"");
                	// logger.debug("permission:"+permission);
                    info.addStringPermission(permission);
                }
                if (StringUtils.isNotBlank(privilege.getApi())){
                    // 添加基于Permission的权限信息
                	String api = privilege.getApi();
//                    logger.debug("permission:"+api);
                    info.addStringPermission(api);
                }
            }
            
            return info;
        } else {
            return null;
        }
    }
    
    /**
     * 设定密码校验的Hash算法与迭代次数
     */
    @PostConstruct
    public void initCredentialsMatcher() {
        HashedCredentialsMatcher matcher = new HashedCredentialsMatcher(SystemService.HASH_ALGORITHM);
        matcher.setHashIterations(SystemService.HASH_INTERATIONS);
        setCredentialsMatcher(matcher);
    }
    
    /**
     * 清空用户关联权限认证，待下次使用时重新加载
     */
    public void clearCachedAuthorizationInfo(String principal) {
        SimplePrincipalCollection principals = new SimplePrincipalCollection(principal, getName());
        clearCachedAuthorizationInfo(principals);
    }

    /**
     * 清空所有关联认证
     */
    public void clearAllCachedAuthorizationInfo() {
        Cache<Object, AuthorizationInfo> cache = getAuthorizationCache();
        if (cache != null) {
            for (Object key : cache.keys()) {
                cache.remove(key);
            }
        }
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

    public static class UserPrincipal implements Serializable {

        private static final long serialVersionUID = 5L;
        
        private int id;
        private String username;
        // private String password;
        private Map<String, Object> cacheMap;

        public UserPrincipal(User user) {
            this.id = user.getId();
            this.username = user.getUsername();
        }

        public int getId() {
            return id;
        }

        // public String getPassword(){
        //     return password;
        // }

        public String getUsername() {
            return username;
        }

        public Map<String, Object> getCacheMap() {
            if (cacheMap==null){
                cacheMap = new HashMap<String, Object>();
            }
            return cacheMap;
        }

    }
    
    /**
     * {@inheritDoc}
     * 重写函数，如果不是web app登录，则用无状态授权
     */
    @Override
    public boolean isPermitted(PrincipalCollection principals, String permission) {
        if (principals.fromRealm(getName()).isEmpty()) {
        	logger.debug("not web app auth");
            return false;
        }
        else {
            return super.isPermitted(principals, permission);
        }
    }
    
    /**
     * {@inheritDoc}
     * 重写函数，如果不是web app登录，则用无状态授权
     */
    @Override
    public boolean hasRole(PrincipalCollection principals, String roleIdentifier) {
        if (principals.fromRealm(getName()).isEmpty()) {
        	logger.debug("not web app auth");
            return false;
        }
        else {
            return super.hasRole(principals, roleIdentifier);
        }
    }
}