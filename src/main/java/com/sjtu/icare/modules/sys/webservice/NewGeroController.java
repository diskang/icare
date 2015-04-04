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

import com.sjtu.icare.common.persistence.Page;
import com.sjtu.icare.common.utils.BasicReturnedJson;
import com.sjtu.icare.common.utils.DateUtils;
import com.sjtu.icare.common.web.rest.MediaTypes;
import com.sjtu.icare.modules.gero.entity.GeroEntity;
import com.sjtu.icare.modules.gero.service.impl.GeroService;
import com.sjtu.icare.modules.op.entity.AreaItemEntity;
import com.sjtu.icare.modules.op.entity.CareItemEntity;
import com.sjtu.icare.modules.op.service.IItemService;
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
	
	@Autowired
	private IItemService itemService;
	
	@RequestMapping(value = "", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
	public Map<String, Object> newGero(@RequestParam("name") String geroName){
			BasicReturnedJson result = new BasicReturnedJson();
			
			// 新建养老院
			
			GeroEntity gero = new GeroEntity();
			gero.setCity(geroName+"_默认城市");
			gero.setName(geroName);
			gero.setDistrict(geroName+"_默认市区");
			gero.setRegisterDate(DateUtils.getDate());
			geroService.insertGero(gero);
			int geroId = gero.getId();
			
			// 插入养老院项目
			// 房间项目
			AreaItemEntity areaQueryEntity = new AreaItemEntity();
			areaQueryEntity.setGeroId(1);
			List<AreaItemEntity> defaultAreaItems = itemService.getAreaItems(areaQueryEntity);
			for (AreaItemEntity areaItem : defaultAreaItems){
				areaItem.setGeroId(geroId);
				itemService.insertAreaItem(areaItem);
			}
			// 老人项目
			CareItemEntity careQueryEntity = new CareItemEntity();
			careQueryEntity.setGeroId(1);
			List<CareItemEntity> defaultCareItems = itemService.getCareItems(careQueryEntity);
			for (CareItemEntity careItem : defaultCareItems){
				careItem.setGeroId(geroId);
				itemService.insertCareItem(careItem);
			}
			
			// 新建养老院管理员			
			User admin = new User();
			admin.setGeroId(geroId);
			admin.setUsername(geroName+"_admin");
			admin.setName(geroName+"_管理员");
			StaffEntity adminStaff = new StaffEntity();
			insertStaff(admin, adminStaff, gero);
			gero.setContactId(adminStaff.getId());
			
			// 新建养老院其他职工
			
			// 房间护工
			User areaCarer = new User();
			areaCarer.setGeroId(geroId);
			areaCarer.setUsername(geroName+"_acarer");
			areaCarer.setName(geroName+"_老人护工");
			StaffEntity areaCarerStaff = new StaffEntity();
			insertStaff(areaCarer, areaCarerStaff, gero);
			
			// 老人护工
			User elderCarer = new User();
			elderCarer.setGeroId(geroId);
			elderCarer.setUsername(geroName+"_ecarer");
			elderCarer.setName(geroName+"_老人护工");
			StaffEntity elderCarerStaff = new StaffEntity();
			insertStaff(elderCarer, elderCarerStaff, gero);
			
			// 医生
			User doctor = new User();
			doctor.setGeroId(geroId);
			doctor.setUsername(geroName+"_doctor");
			doctor.setName(geroName+"_医生");
			StaffEntity doctorStaff = new StaffEntity();
			insertStaff(doctor, doctorStaff, gero);
			
			
			// 新建养老院基本角色
			List<Role> defaultRoleList = getDefaultRoleList();
			for (Role role : defaultRoleList){
				role.setGeroId(geroId);
				if (!role.getName().equals("超级管理员")) {
					systemService.insertGeroRole(role);
					systemService.insertRolePrivilege(role);
					role = systemService.getRoleByNameAndGero(role);
				}
				
				// 给职工赋予角色
				List<Role> tmpList = new ArrayList<Role>();
				tmpList.add(role);
				if (role.getName().equals("管理员")) {
					admin.setRoleList(tmpList);
					systemService.updateUserRoles(admin);
				}else if (role.getName().equals("老人护工")) {
					elderCarer.setRoleList(tmpList);
					systemService.updateUserRoles(elderCarer);
				}else if (role.getName().equals("房间护工")) {
					areaCarer.setRoleList(tmpList);
					systemService.updateUserRoles(areaCarer);
				}else if (role.getName().equals("医生")) {
					doctor.setRoleList(tmpList);
					systemService.updateUserRoles(doctor);
				}
			}
			
			// 更新数据
			geroService.updateGero(gero);
			
			// 返回结果
			
			result.addEntity(gero);
			result.addEntity(admin);
			result.addEntity(adminStaff);
			
			return result.getMap();
	}
	
	private void insertStaff(User user, StaffEntity staffEntity, GeroEntity gero) {
		staffEntity.setName(user.getName());
		staffEntity.setGeroId(gero.getId());
		staffDataService.insertStaff(staffEntity);
		int staffId = staffEntity.getId();
		
		user.setGeroId(gero.getId());
		user.setPassword("02a3f0772fcca9f415adc990734b45c6f059c7d33ee28362c4852032");
		user.setUserType(1);
		user.setUserId(staffId);
		user.setRegisterDate(DateUtils.getDate());
		user.setIdentityNo("000");
		user.setBirthday(DateUtils.getDate());
		user.setPhoneNo("888");
		systemService.insertUser(user);
		user = UserUtils.getByLoginName(user.getUsername());
		int userId = user.getId();
	}
	
	private List<Role> getDefaultRoleList () {
		Page<Role> rolePage = new Page<Role>(1,100);
		List<Role> defaultRoleList = systemService.getRolePageFromGeroId(rolePage, new Gero(1)).getList();
		for (Role role : defaultRoleList){
			role = systemService.getPrivilegeListByRole(role);
		}
		return defaultRoleList;
	}
}
