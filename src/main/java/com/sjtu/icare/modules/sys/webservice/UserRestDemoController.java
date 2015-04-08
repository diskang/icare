//package com.sjtu.icare.modules.sys.webservice;
//
//import java.net.URI;
//
//import javax.validation.Validator;
//
//import org.apache.log4j.Logger;
//import org.apache.shiro.authz.annotation.RequiresPermissions;
//import org.apache.shiro.authz.annotation.RequiresRoles;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.ResponseStatus;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.util.UriComponentsBuilder;
//
//import com.sjtu.icare.common.beanvalidator.BeanValidators;
//import com.sjtu.icare.common.utils.SpringContextHolder;
//import com.sjtu.icare.common.web.rest.MediaTypes;
//import com.sjtu.icare.common.web.rest.RestException;
//import com.sjtu.icare.modules.sys.entity.User;
//import com.sjtu.icare.modules.sys.persistence.UserMapper;
//
//
//
///**
// *
// * Demo中并未加入shiro的支持，如果加入建议：
// * Shiro的配置文件中对/api/**进行拦截，要求authBasic或其他认证.
// * 
// * 以下demo了几个操作GET/POST/DELETE方法
// * POST   /api/v1/user
// * GET    /api/v1/user/{username}
// * DELETE /api/v1/user/{id}
// * 注意 路径参数分别是id和username
// * 
// * @author KangShiyong
// */
//@RestController
////@RequestMapping(value = { "/api/v1/user", "/api/secure/v1/user" })
//public class UserRestDemoController {
//	private static Logger logger = Logger.getLogger(UserRestDemoController.class);
//	
//	@Autowired
//	private Validator validator;
//	
//	/*此处直接取了DAO层用于demo，应该使用Service层接口*/
//	private static UserMapper userMapper = SpringContextHolder.getBean(UserMapper.class);
//	
//	/**如果去掉produce=JSON
//	 * 基于ContentNegotiationManager,根据URL的后缀渲染不同的格式
//	 * eg. /api/v1/user/1.xml 返回xml
//	 * /api/v1/user/1.json 返回json
//	 * /api/v1/user/1 返回json(why?)
//	 * 
//	 * 目前加了produce，只能返回json了
//	 */
//	@RequestMapping(value = "/{username}", method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
//	/*@RequiresPermissions("admin")*/
//	public User getUserInPath(@PathVariable("username") String username) {
//		
//		User user = userMapper.getByUsername(new User(-1,username));
//
//		if (user == null) {
//			String message = "用户不存在(username:" + username + ")";
//			logger.warn(message);
//			//错误处理见common/web/rest/RestExceptionHandler.java
//			//Exception见common/web/rest/RestException.java
//			throw new RestException(HttpStatus.NOT_FOUND, message);
//		}
//
//		return user;
//	}
//	
//	@RequestMapping(method = RequestMethod.POST)
//	public ResponseEntity<?> create( User user, UriComponentsBuilder uriBuilder) {
//		// 调用JSR303 Bean Validator进行校验, 异常将由RestExceptionHandler统一处理.
//		// 见 common/web/rest/RestExceptionHandler.java
//		BeanValidators.validateWithException(validator, user);
//
//		// 保存用户
//		userMapper.insert(user);
//		// 按照Restful风格约定，创建指向新任务的url, 也可以直接返回id或对象.
//		String username = user.getUsername();
//		URI uri = uriBuilder.path("/api/v1/user/" + username).build().toUri();
//		HttpHeaders headers = new HttpHeaders();
//		headers.setLocation(uri);
//		
//		return new ResponseEntity(headers, HttpStatus.CREATED);
//	}
//	
//	/*用void 方法必须有ResponseStatus注解*/
//	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
//	@ResponseStatus(HttpStatus.NO_CONTENT)
//	public void delete(@PathVariable("id") int id) {
//		userMapper.delete(new User(id));
//	}
//	
//}
