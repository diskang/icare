/**
 * @Package com.sjtu.icare.modules.staff.service
 * @Description TODO
 * @date Mar 15, 2015 6:05:50 PM
 * @author Wang Qi
 * @version TODO
 */
package com.sjtu.icare.modules.staff.service;

import java.util.List;

import com.sjtu.icare.modules.elder.entity.ElderEntity;
import com.sjtu.icare.modules.staff.entity.StaffEntity;

public interface IDutyCarerService {
	
	List<StaffEntity> getDutyCarerByElderIdAndDate(ElderEntity elderEntity, String date);

}
