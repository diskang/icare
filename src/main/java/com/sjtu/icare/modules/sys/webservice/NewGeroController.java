package com.sjtu.icare.modules.sys.webservice;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sjtu.icare.common.utils.BasicReturnedJson;
import com.sjtu.icare.common.utils.DateUtils;
import com.sjtu.icare.common.web.rest.MediaTypes;
import com.sjtu.icare.modules.gero.entity.GeroEntity;
import com.sjtu.icare.modules.gero.service.impl.GeroService;
import com.sjtu.icare.modules.staff.entity.StaffEntity;
import com.sjtu.icare.modules.staff.service.impl.StaffDataService;
import com.sjtu.icare.modules.sys.entity.Gero;
import com.sjtu.icare.modules.sys.entity.Role;
import com.sjtu.icare.modules.sys.entity.User;
import com.sjtu.icare.modules.sys.service.SystemService;
import com.sjtu.icare.modules.sys.utils.UserUtils;

@RestController
@RequestMapping({"/newGero"})
public class NewGeroController {
	private static Logger logger = Logger.getLogger(NewGeroController.class);
	
	@Autowired
	private SystemService systemService;
	
	@Autowired
	private GeroService geroService;
	
	@Autowired
	private StaffDataService staffDataService;
	
	@RequestMapping(value = "", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
	public Map<String, Object> newGero(@RequestParam("name") String geroName){
			BasicReturnedJson result = new BasicReturnedJson();
			GeroEntity gero = new GeroEntity();
			gero.setCity(geroName+"_city");
			gero.setName(geroName+"_name");
			gero.setDistrict(geroName+"_dist");
			gero.setRegisterDate(DateUtils.getDate());
			geroService.insertGero(gero);
			int geroId = gero.getId();
			
			User admin = new User();
			admin.setGeroId(geroId);
			admin.setUsername(geroName+"_admin");
			admin.setName(geroName+"_admin_name");
			admin.setPassword("02a3f0772fcca9f415adc990734b45c6f059c7d33ee28362c4852032");
			admin.setUserType(2);
			admin.setUserId(0);
			admin.setRegisterDate(DateUtils.getDate());
			admin.setIdentityNo("000");
			admin.setBirthday(DateUtils.getDate());
			admin.setPhoneNo("888");
			systemService.insertUser(admin);
			admin = UserUtils.getByLoginName(geroName+"_admin");
			int userId = admin.getId();
			
			StaffEntity adminStaff = new StaffEntity();
			adminStaff.setName(geroName+"_admin_name");
			adminStaff.setGeroId(geroId);
			staffDataService.insertStaff(adminStaff);
			int staffId = adminStaff.getId();
			
			admin.setUserId(staffId);
			gero.setContactId(staffId);
			
			Role role = new Role();
			role.setName("admin");
			role.setGeroId(1);
			role = systemService.getRoleByNameAndGero(role);
			role = systemService.getPrivilegeListByRole(role);
			role.setGeroId(geroId);
			List<Integer> userIdList = new ArrayList<Integer>();
			userIdList.add(userId);
			role.setUserIdList(userIdList);
			systemService.insertGeroRole(role);
			systemService.insertRolePrivilege(role);
			systemService.updateRoleUser(role);
			systemService.updateUser(admin);
			geroService.updateGero(gero);
			result.addEntity(gero);
			result.addEntity(admin);
			result.addEntity(adminStaff);
			
			return result.getMap();
	}
}
