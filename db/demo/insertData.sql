USE [HCDB_UPDATE_TEST]
GO

-- 建立默认养老院

INSERT INTO [dbo].[T_GERO]([name],[city],[district],[register_date])
     VALUES('default','default','default','2015-03-12')
GO

-- 建立权限表

insert into T_PRIVILEGE(name,parent_id,parent_ids,permission,href,api,icon,notes)values
('root','0','0,','','','','',''),('用户','1','0,1','','','','',''),('用户个人信息','2','0,1,2','','','','',''),('查看当前用户信息','3','0,1,2,3','','','','',''),('查看当前超管信息','4','0,1,2,3,4','user:{uid}:info:read','/user/1','/user/{id}:GET','',''),('查看当前员工信息','4','0,1,2,3,4','user:{uid}:info:read','','/gero/{id}/staff/{id}:GET','',''),('查看当前老人信息','4','0,1,2,3,4','user:{uid}:info:read','','/gero/{id}/elder/{id}:GET','',''),('查看当前家属信息','4','0,1,2,3,4','user:{uid}:info:read','','','',''),('修改当前用户信息','3','0,1,2,3','','no','','',''),('修改当前超管信息','9','0,1,2,3,9','user:{uid}:info:update','','/user/{id}:PUT','',''),('修改当前员工信息','9','0,1,2,3,9','user:{uid}:info:update','','/gero/{id}/staff/{id}:PUT','',''),('修改当前老人信息','9','0,1,2,3,9','user:{uid}:info:update','','/gero/{id}/elder/{id}:PUT','',''),('修改当前家属信息','9','0,1,2,3,9','user:{uid}:info:update','','','',''),('修改用户密码','3','0,1,2,3','user:{uid}:password:update','no','/user/{id}/password:PUT','',''),('老人家属功能模块','2','0,1,2','','no','','',''),('查看老人健康信息','15','0,1,2,15','','','','',''),('查看老人体温','16','0,1,2,15,16','elder:{eid}:health:read','','/gero/{id}/elder/{id}/temperature:GET','',''),('查看老人血压','16','0,1,2,15,16','elder:{eid}:health:read','','/gero/{id}/elder/{id}/blood_pressure:GET','',''),('查看老人心律','16','0,1,2,15,16','elder:{eid}:health:read','','/gero/{id}/elder/{id}/heart_rate:GET','',''),('查看老人项目记录','15','0,1,2,15','elder:{eid}:carework_record:read','','/gero/{id}/carework_record:GET','',''),('查看老人负责护工','15','0,1,2,15','elder:{eid}:carework:read','','/gero/{id}/carework:GET','',''),('查看老人项目','15','0,1,2,15','elder:{eid}:care_item:read','','/gero/{id}/elder/{id}/care_item:GET','',''),('职工功能模块','2','0,1,2','','','','',''),('养老院信息','23','0,1,2,23','','','','',''),('查看养老院老人','24','0,1,2,23,24','staff:{sid}:gero:{gid}:elder:read','/gero/1/elder','/gero/{id}/elder/{id}:GET','',''),('查看养老院基本信息','24','0,1,2,23,24','staff:{sid}:gero:{gid}:info:read','','/gero/{id}:GET','',''),('获取养老院所有的区域列表','24','0,1,2,23,24','staff:{sid}:gero:{gid}:area:read','','/gero/{id}/area:GET','',''),('获取某区域及其子区域','24','0,1,2,23,24','staff:{sid}:gero:{gid}:area:read','','/gero/{id}/area/{id}:GET','',''),('查看本人排班信息','23','0,1,2,23','','','','',''),('获取某员工的上班信息列表','29','0,1,2,23,29','staff:{sid}:schedule:read','','/gero/{id}/staff/{id}/schedule:GET','',''),('获取某员工的某天是否上班','29','0,1,2,23,29','staff:{sid}:schedule:read','','/staff/{id}/schedule/{date}:GET','',''),('养老院管理模块','23','0,1,2,23','','','','',''),('养老院基本信息','32','0,1,2,23,32','','','','',''),('获取养老院基本信息','33','0,1,2,23,32,33','admin:gero:{gid}:info:read','','/gero/{id}:GET','',''),('修改养老院基本信息','33','0,1,2,23,32,33','admin:gero:{gid}:info:update','','/gero/{id}:PUT','',''),('区域管理','32','0,1,2,23,32','','','','',''),('获取养老院所有的区域列表','36','0,1,2,23,32,36','admin:gero:{gid}:area:read','','/gero/{id}/area:GET','',''),('创建一个养老院区域','36','0,1,2,23,32,36','admin:gero:{gid}:area:add','','/gero/{id}/area:POST','',''),('获取某区域及其子区域','36','0,1,2,23,32,36','admin:gero:{gid}:area:read','','/gero/{id}/area/{id}:GET','',''),('修改某区域','36','0,1,2,23,32,36','admin:gero:{gid}:area:update','','/gero/{id}/area/{id}:PUT','',''),('删除某区域','36','0,1,2,23,32,36','admin:gero:{gid}:area:delete','','/gero/{id}/area/{id}:DELETE','',''),('项目管理','32','0,1,2,23,32','','','','',''),('老人项目','42','0,1,2,23,32,42','','','','',''),('老人项目列表','43','0,1,2,23,32,42,43','admin:gero:{gid}:elder_item:read','/gero/1/care_item','/gero/{id}/care_item:GET','',''),('添加老人项目','43','0,1,2,23,32,42,43','admin:gero:{gid}:elder_item:add','','/gero/{id}/care_item:POST','',''),('取老人项目','43','0,1,2,23,32,42,43','admin:gero:{gid}:elder_item:read','','/gero/{id}/care_item/{id}:GET','',''),('改老人项目','43','0,1,2,23,32,42,43','admin:gero:{gid}:elder_item:update','','/gero/{id}/care_item/{id}:PUT','',''),('删老人项目','43','0,1,2,23,32,42,43','admin:gero:{gid}:elder_item:delete','','/gero/{id}/care_item/{id}:DELETE','',''),('房间项目','42','0,1,2,23,32,42','','','','',''),('房间项目列表','49','0,1,2,23,32,42,49','admin:gero:{gid}:area_item:read','/gero/1/area_item','/gero/{id}/area_item:GET','',''),('添加房间项目','49','0,1,2,23,32,42,49','admin:gero:{gid}:area_item:add','','/gero/{id}/area_item:POST','',''),('取房间项目','49','0,1,2,23,32,42,49','admin:gero:{gid}:area_item:read','','/gero/{id}/area_item/{id}:GET','',''),('改房间项目','49','0,1,2,23,32,42,49','admin:gero:{gid}:area_item:update','','/gero/{id}/area_item/{id}:PUT','',''),('删房间项目','49','0,1,2,23,32,42,49','admin:gero:{gid}:area_item:delete','','/gero/{id}/area_item/{id}:DELETE','',''),('老人专属项目','42','0,1,2,23,32,42','','no','','',''),('老人专属项目列表','55','0,1,2,23,32,42,55','admin:gero:{gid}:elder:elder_item:read','','/gero/{id}/elder/{id}/care_item:GET','',''),('添加老人专属项目','55','0,1,2,23,32,42,55','admin:gero:{gid}:elder:elder_item:add','','/gero/{id}/elder/{id}/care_item:POST','',''),('取老人专属项目','55','0,1,2,23,32,42,55','admin:gero:{gid}:elder:elder_item:read','','/gero/{id}/elder/{id}/care_item/{id}:GET','',''),('改老人专属项目','55','0,1,2,23,32,42,55','admin:gero:{gid}:elder:elder_item:update','','/gero/{id}/elder/{id}/care_item/{id}:PUT','',''),('删老人专属项目','55','0,1,2,23,32,42,55','admin:gero:{gid}:elder:elder_item:delete','','/gero/{id}/elder/{id}/care_item/{id}:DELETE','',''),('院属互动','32','0,1,2,23,32','','no','','',''),('院属互动列表','61','0,1,2,23,32,61','admin:gero:{gid}:exchange:read','','/gero/{id}/exchange:GET','',''),('添加院属互动','61','0,1,2,23,32,61','admin:gero:{gid}:exchange:add','','/gero/{id}/exchange:POST','',''),('取院属互动','61','0,1,2,23,32,61','admin:gero:{gid}:exchange:read','','/gero/{id}/exchange/{id}:GET','',''),('改院属互动','61','0,1,2,23,32,61','admin:gero:{gid}:exchange:update','','/gero/{id}/exchange/{id}:PUT','',''),('删院属互动','61','0,1,2,23,32,61','admin:gero:{gid}:exchange:delete','','/gero/{id}/exchange/{id}:DELETE','',''),('角色管理','32','0,1,2,23,32','','','','',''),('养老院角色列表','67','0,1,2,23,32,67','admin:gero:{gid}:role:read','/gero/1/role','/gero/{id}/role:GET','',''),('新增养老院角色','67','0,1,2,23,32,67','admin:gero:{gid}:role:add','','/gero/{id}/role:POST','',''),('获取角色信息','67','0,1,2,23,32,67','admin:gero:{gid}:role:read','','/gero/{id}/role/{id}:GET','',''),('修改角色的基本信息','67','0,1,2,23,32,67','admin:gero:{gid}:role:update','','/gero/{id}/role/{id}:PUT','',''),('删除养老院角色','67','0,1,2,23,32,67','admin:gero:{gid}:role:delete','','/gero/{id}/role/{id}:DELETE','',''),('给养老院角色添加权限','67','0,1,2,23,32,67','admin:gero:{gid}:role:update','','/gero/{id}/role/{id}/privilege:POST','',''),('给养老院角色删除权限','67','0,1,2,23,32,67','admin:gero:{gid}:role:update','','/gero/{id}/role/{id}/privilege:DELETE','',''),('为某个角色关联员工','67','0,1,2,23,32,67','admin:gero:{gid}:role:update','','/gero/{id}/role/{id}/user:PUT','',''),('职工基本信息管理','32','0,1,2,23,32','','','','',''),('职工基本信息列表','76','0,1,2,23,32,76','admin:gero:{gid}:staff:info:read','/gero/1/staff','/gero/{id}/staff:GET','',''),('增加职工基本信息','76','0,1,2,23,32,76','admin:gero:{gid}:staff:info:add','','/gero/{id}/staff:POST','',''),('查找职工基本信息','76','0,1,2,23,32,76','admin:gero:{gid}:staff:info:read','','/gero/{id}/staff/{id}:GET','',''),('修改职工基本信息','76','0,1,2,23,32,76','admin:gero:{gid}:staff:info:update','','/gero/{id}/staff/{id}:PUT','',''),('删除职工基本信息','76','0,1,2,23,32,76','admin:gero:{gid}:staff:info:delete','','/gero/{id}/staff/{id}:DELETE','',''),('老人基本信息管理','32','0,1,2,23,32','','','','',''),('老人基本信息列表','82','0,1,2,23,32,82','admin:gero:{gid}:elder:info:read','','/gero/{id}/elder:GET','',''),('新增老人基本信息','82','0,1,2,23,32,82','admin:gero:{gid}:elder:info:add','','/gero/{id}/elder:POST','',''),('修改老人基本信息','82','0,1,2,23,32,82','admin:gero:{gid}:elder:info:read','','/gero/{id}/elder/{id}:GET','',''),('查找老人基本信息','82','0,1,2,23,32,82','admin:gero:{gid}:elder:info:update','','/gero/{id}/elder/{id}:PUT','',''),('删除老人基本信息','82','0,1,2,23,32,82','admin:gero:{gid}:elder:info:delete','','/gero/{id}/elder/{id}:DELETE','',''),('老人健康信息管理','32','0,1,2,23,32','','','','',''),('获取体温','88','0,1,2,23,32,88','admin:gero:{gid}:elder:health:read','','/gero/{id}/elder/{id}/temperature:GET','',''),('新增体温','88','0,1,2,23,32,88','admin:gero:{gid}:elder:health:add','','/gero/{id}/elder/{id}/temperature:POST','',''),('获取血压','88','0,1,2,23,32,88','admin:gero:{gid}:elder:health:read','','/gero/{id}/elder/{id}/blood_pressure:GET','',''),('新增血压','88','0,1,2,23,32,88','admin:gero:{gid}:elder:health:add','','/gero/{id}/elder/{id}/blood_pressure:POST','',''),('获取心率','88','0,1,2,23,32,88','admin:gero:{gid}:elder:health:read','','/gero/{id}/elder/{id}/heart_rate:GET','',''),('新增心率','88','0,1,2,23,32,88','admin:gero:{gid}:elder:health:add','','/gero/{id}/elder/{id}/heart_rate:POST','',''),('项目记录管理','32','0,1,2,23,32','','','','',''),('老人护工','95','0,1,2,23,32,95','','','','',''),('老人护工的项目记录列表','96','0,1,2,23,32,95,96','admin:gero:{gid}:carework_record:read','','/gero/{id}/carework_record:GET','',''),('添加老人护工项目记录','96','0,1,2,23,32,95,96','admin:gero:{gid}:carework_record:add','','/gero/{id}/carework_record:POST','',''),('房间护工','95','0,1,2,23,32,95','','','','',''),('房间护工的项目记录列表','99','0,1,2,23,32,95,99','admin:gero:{gid}:areawork_record:read','','/gero/{id}/areawork_record:GET','',''),('添加房间护工项目记录','99','0,1,2,23,32,95,99','admin:gero:{gid}:areawork_record:add','','/gero/{id}/areawork_record:POST','',''),('排班管理','32','0,1,2,23,32','','','','',''),('上班时间安排','102','0,1,2,23,32,102','','','','',''),('上班时间安排列表','103','0,1,2,23,32,102,103','admin:gero:{gid}:schedule:read','/gero/1/schedule','/gero/{id}/schedule:GET','',''),('修改上班时间安排','103','0,1,2,23,32,102,103','admin:gero:{gid}:schedule:update','','/gero/{id}/schedule:PUT','',''),('老人护工职责','102','0,1,2,23,32,102','','no','','',''),('老人护工职责列表','106','0,1,2,23,32,102,106','admin:gero:{gid}:carework:read','','/gero/{id}/carework:GET','',''),('新增老人护工职责','106','0,1,2,23,32,102,106','admin:gero:{gid}:carework:add','','/gero/{id}/carework:POST','',''),('修改老人护工职责','106','0,1,2,23,32,102,106','admin:gero:{gid}:carework:update','','/gero/{id}/carework/{id}:PUT','',''),('删除老人护工职责','106','0,1,2,23,32,102,106','admin:gero:{gid}:carework:delete','','/gero/{id}/carework/{id}:DELETE','',''),('房间护工职责','102','0,1,2,23,32,102','','no','','',''),('房间护工职责列表','111','0,1,2,23,32,102,111','admin:gero:{gid}:areawork:read','','/gero/{id}/areawork:GET','',''),('新增房间护工职责','111','0,1,2,23,32,102,111','admin:gero:{gid}:areawork:add','','/gero/{id}/areawork:POST','',''),('修改房间护工职责','111','0,1,2,23,32,102,111','admin:gero:{gid}:areawork:update','','/gero/{id}/areawork/{id}:PUT','',''),('删除房间护工职责','111','0,1,2,23,32,102,111','admin:gero:{gid}:areawork:delete','','/gero/{id}/areawork/{id}:DELETE','',''),('护工模块','23','0,1,2,23','','','','',''),('查看项目','116','0,1,2,23,116','','no','','',''),('老人项目列表','117','0,1,2,23,116,117','carer:{cid}:gero:{gid}:care_item:read','','/gero/{id}/care_item:GET','',''),('老人专属项目列表','117','0,1,2,23,116,117','carer:{cid}:gero:{gid}:elder:care_item:read','','/gero/{id}/elder/{id}/care_item:GET','',''),('房间项目列表','117','0,1,2,23,116,117','carer:{cid}:gero:{gid}:area_item:read','','/gero/{id}/area_item:GET','',''),('查询老人项目','117','0,1,2,23,116,117','carer:{cid}:gero:{gid}:care_item:read','','/gero/{id}/care_item/{id}:GET','',''),('查询老人专属项目','117','0,1,2,23,116,117','carer:{cid}:gero:{gid}:elder:care_item:read','','/gero/{id}/elder/{id}/care_item/{id}:GET','',''),('查询房间项目','117','0,1,2,23,116,117','carer:{cid}:gero:{gid}:area_item:read','','/gero/{id}/area_item/{id}:GET','',''),('项目记录','116','0,1,2,23,116','','','','',''),('老人护工查看','124','0,1,2,23,116,124','carer:{cid}:gero:{gid}:carework_record:read','','/gero/{id}/carework_record','',''),('老人护工新增','124','0,1,2,23,116,124','carer:{cid}:gero:{gid}:carework_record:add','','/gero/{id}/carework_record','',''),('房间护工查看','124','0,1,2,23,116,124','carer:{cid}:gero:{gid}:areawork_record:read','','/gero/{id}/areawork_record','',''),('房间护工新增','124','0,1,2,23,116,124','carer:{cid}:gero:{gid}:areawork_record:add','','/gero/{id}/areawork_record','',''),('查看职责','116','0,1,2,23,116','','','','',''),('老人护工','129','0,1,2,23,116,129','carer:{cid}:gero:{gid}:duty_carer:read','','/gero/{id}/elder/{id}/duty_carer','',''),('房间护工','129','0,1,2,23,116,129','carer:{cid}:gero:{gid}:duty_carer:read','','/gero/{id}/area/{id}/duty_carer','',''),('医生模块','23','0,1,2,23','','no','','',''),('老人健康信息管理','132','0,1,2,23,132','','','','',''),('获取体温','133','0,1,2,23,132,133','doctor:gero:{gid}:elder:health:read','','/gero/{id}/elder/{id}/temperature:GET','',''),('新增体温','133','0,1,2,23,132,133','doctor:gero:{gid}:elder:health:add','','/gero/{id}/elder/{id}/temperature:POST','',''),('获取血压','133','0,1,2,23,132,133','doctor:gero:{gid}:elder:health:read','','/gero/{id}/elder/{id}/blood_pressure:GET','',''),('新增血压','133','0,1,2,23,132,133','doctor:gero:{gid}:elder:health:add','','/gero/{id}/elder/{id}/blood_pressure:POST','',''),('获取心率','133','0,1,2,23,132,133','doctor:gero:{gid}:elder:health:read','','/gero/{id}/elder/{id}/heart_rate:GET','',''),('新增心率','133','0,1,2,23,132,133','doctor:gero:{gid}:elder:health:add','','/gero/{id}/elder/{id}/heart_rate:POST','',''),('修改养老院老人密码','82','0,1,2,23,32,82','admin:gero:{gid}:staff:info:update','','/user/{id}/password:PUT','',''),('修改养老院职工密码','76','0,1,2,23,32,76','admin:gero:{gid}:elder:info:update','','/user/{id}/password:PUT','',''),('修改员工角色','67','0,1,2,23,32,67','admin:gero:{gid}:user:role:update','','/user/{id}/role:PUT','',''),('超级管理员','2','0,1,2','','','','',''),('查询用户列表','143','0,1,2,143','super:user:list:read','','/user:GET','',''),('修改上班时间安排','103','0,1,2,23,32,102,103','admin:gero:{gid}:schedule:add','','/gero/{id}/staff/{id}/schedule:POST','','')
GO

---------------------------------------------------------------------------------------

-- 建立养老院区域

INSERT INTO [dbo].[T_AREA]
           ([parent_id]
           ,[parent_ids]
           ,[gero_id]
           ,[type]
           ,[level]
           ,[name]
           ,[full_name])
     VALUES
           (0
           ,'0,'
           ,1
           ,1
           ,1
           ,'1号楼'
           ,'1号楼,')
GO

INSERT INTO [dbo].[T_AREA]
           ([parent_id]
           ,[parent_ids]
           ,[gero_id]
           ,[type]
           ,[level]
           ,[name]
           ,[full_name])
     VALUES
           (1
           ,'0,1'
           ,1
           ,2
           ,2
           ,'1层'
           ,'1号楼,1层,')
GO

INSERT INTO [dbo].[T_AREA]
           ([parent_id]
           ,[parent_ids]
           ,[gero_id]
           ,[type]
           ,[level]
           ,[name]
           ,[full_name])
     VALUES
           (2
           ,'0,1,2'
           ,1
           ,3
           ,3
           ,'1室'
           ,'1号楼,1层,1室,')
GO

INSERT INTO [dbo].[T_AREA]
           ([parent_id]
           ,[parent_ids]
           ,[gero_id]
           ,[type]
           ,[level]
           ,[name]
           ,[full_name])
     VALUES
           (3
           ,'0,1,2,3'
           ,1
           ,4
           ,4
           ,'1床'
           ,'1号楼,1层,1室,1床')
GO

---------------------------------------------------------------------------------------

-- 生成默认养老院房间项目

INSERT INTO [dbo].[T_AREA_ITEM]
           ([gero_id]
           ,[name]
           ,[icon]
           ,[period]
           ,[frequency]
           ,[notes])
     VALUES
           (1
           ,'漱口'
           ,'item.png'
           ,1
           ,1
           ,'')
GO

INSERT INTO [dbo].[T_AREA_ITEM]
           ([gero_id]
           ,[name]
           ,[icon]
           ,[period]
           ,[frequency]
           ,[notes])
     VALUES
           (1
           ,'刷牙'
           ,'item.png'
           ,1
           ,1
           ,'')
GO

INSERT INTO [dbo].[T_AREA_ITEM]
           ([gero_id]
           ,[name]
           ,[icon]
           ,[period]
           ,[frequency]
           ,[notes])
     VALUES
           (1
           ,'梳头'
           ,'item.png'
           ,1
           ,1
           ,'')
GO

INSERT INTO [dbo].[T_AREA_ITEM]
           ([gero_id]
           ,[name]
           ,[icon]
           ,[period]
           ,[frequency]
           ,[notes])
     VALUES
           (1
           ,'更换衣服'
           ,'item.png'
           ,1
           ,1
           ,'')
GO

INSERT INTO [dbo].[T_AREA_ITEM]
           ([gero_id]
           ,[name]
           ,[icon]
           ,[period]
           ,[frequency]
           ,[notes])
     VALUES
           (1
           ,'发放药物'
           ,'item.png'
           ,1
           ,1
           ,'')
GO

INSERT INTO [dbo].[T_AREA_ITEM]
           ([gero_id]
           ,[name]
           ,[icon]
           ,[period]
           ,[frequency]
           ,[notes])
     VALUES
           (1
           ,'剃须'
           ,'item.png'
           ,1
           ,1
           ,'')
GO

INSERT INTO [dbo].[T_AREA_ITEM]
           ([gero_id]
           ,[name]
           ,[icon]
           ,[period]
           ,[frequency]
           ,[notes])
     VALUES
           (1
           ,'剪指甲'
           ,'item.png'
           ,1
           ,1
           ,'')
GO

INSERT INTO [dbo].[T_AREA_ITEM]
           ([gero_id]
           ,[name]
           ,[icon]
           ,[period]
           ,[frequency]
           ,[notes])
     VALUES
           (1
           ,'饮水'
           ,'item.png'
           ,1
           ,1
           ,'')
GO

INSERT INTO [dbo].[T_AREA_ITEM]
           ([gero_id]
           ,[name]
           ,[icon]
           ,[period]
           ,[frequency]
           ,[notes])
     VALUES
           (1
           ,'穿（脱）衣'
           ,'item.png'
           ,1
           ,1
           ,'')
GO

INSERT INTO [dbo].[T_AREA_ITEM]
           ([gero_id]
           ,[name]
           ,[icon]
           ,[period]
           ,[frequency]
           ,[notes])
     VALUES
           (1
           ,'协助如厕'
           ,'item.png'
           ,1
           ,1
           ,'')
GO

INSERT INTO [dbo].[T_AREA_ITEM]
           ([gero_id]
           ,[name]
           ,[icon]
           ,[period]
           ,[frequency]
           ,[notes])
     VALUES
           (1
           ,'喂食'
           ,'item.png'
           ,1
           ,1
           ,'')
GO

INSERT INTO [dbo].[T_AREA_ITEM]
           ([gero_id]
           ,[name]
           ,[icon]
           ,[period]
           ,[frequency]
           ,[notes])
     VALUES
           (1
           ,'更换尿布'
           ,'item.png'
           ,1
           ,1
           ,'')
GO

INSERT INTO [dbo].[T_AREA_ITEM]
           ([gero_id]
           ,[name]
           ,[icon]
           ,[period]
           ,[frequency]
           ,[notes])
     VALUES
           (1
           ,'衣物洗涤'
           ,'item.png'
           ,1
           ,1
           ,'')
GO

INSERT INTO [dbo].[T_AREA_ITEM]
           ([gero_id]
           ,[name]
           ,[icon]
           ,[period]
           ,[frequency]
           ,[notes])
     VALUES
           (1
           ,'尿布洗涤'
           ,'item.png'
           ,1
           ,1
           ,'')
GO

INSERT INTO [dbo].[T_AREA_ITEM]
           ([gero_id]
           ,[name]
           ,[icon]
           ,[period]
           ,[frequency]
           ,[notes])
     VALUES
           (1
           ,'食物粉碎'
           ,'item.png'
           ,1
           ,1
           ,'')
GO

---------------------------------------------------------------------------------------

-- 生成默认养老院老人项目

INSERT INTO [dbo].[T_CARE_ITEM]
           ([gero_id]
           ,[name]
           ,[icon]
           ,[level]
           ,[period]
           ,[frequency]
           ,[notes])
     VALUES
           (1
           ,'漱口'
           ,'item.png'
		   ,1
           ,1
           ,1
           ,'')
GO

INSERT INTO [dbo].[T_CARE_ITEM]
           ([gero_id]
           ,[name]
           ,[icon]
           ,[level]
           ,[period]
           ,[frequency]
           ,[notes])
     VALUES
           (1
           ,'刷牙'
           ,'item.png'
		   ,1
           ,1
           ,1
           ,'')
GO

INSERT INTO [dbo].[T_CARE_ITEM]
           ([gero_id]
           ,[name]
           ,[icon]
           ,[level]
           ,[period]
           ,[frequency]
           ,[notes])
     VALUES
           (1
           ,'梳头'
           ,'item.png'
		   ,1
           ,1
           ,1
           ,'')
GO

INSERT INTO [dbo].[T_CARE_ITEM]
           ([gero_id]
           ,[name]
           ,[icon]
           ,[level]
           ,[period]
           ,[frequency]
           ,[notes])
     VALUES
           (1
           ,'更换衣服'
           ,'item.png'
		   ,1
           ,1
           ,1
           ,'')
GO

INSERT INTO [dbo].[T_CARE_ITEM]
           ([gero_id]
           ,[name]
           ,[icon]
           ,[level]
           ,[period]
           ,[frequency]
           ,[notes])
     VALUES
           (1
           ,'发放药物'
           ,'item.png'
		   ,1
           ,1
           ,1
           ,'')
GO

INSERT INTO [dbo].[T_CARE_ITEM]
           ([gero_id]
           ,[name]
           ,[icon]
           ,[level]
           ,[period]
           ,[frequency]
           ,[notes])
     VALUES
           (1
           ,'剃须'
           ,'item.png'
		   ,1
           ,1
           ,1
           ,'')
GO

INSERT INTO [dbo].[T_CARE_ITEM]
           ([gero_id]
           ,[name]
           ,[icon]
           ,[level]
           ,[period]
           ,[frequency]
           ,[notes])
     VALUES
           (1
           ,'剪指甲'
           ,'item.png'
		   ,1
           ,1
           ,1
           ,'')
GO

INSERT INTO [dbo].[T_CARE_ITEM]
           ([gero_id]
           ,[name]
           ,[icon]
           ,[level]
           ,[period]
           ,[frequency]
           ,[notes])
     VALUES
           (1
           ,'饮水'
           ,'item.png'
		   ,1
           ,1
           ,1
           ,'')
GO

INSERT INTO [dbo].[T_CARE_ITEM]
           ([gero_id]
           ,[name]
           ,[icon]
           ,[level]
           ,[period]
           ,[frequency]
           ,[notes])
     VALUES
           (1
           ,'穿（脱）衣'
           ,'item.png'
		   ,1
           ,1
           ,1
           ,'')
GO

INSERT INTO [dbo].[T_CARE_ITEM]
           ([gero_id]
           ,[name]
           ,[icon]
           ,[level]
           ,[period]
           ,[frequency]
           ,[notes])
     VALUES
           (1
           ,'协助如厕'
           ,'item.png'
		   ,1
           ,1
           ,1
           ,'')
GO

INSERT INTO [dbo].[T_CARE_ITEM]
           ([gero_id]
           ,[name]
           ,[icon]
           ,[level]
           ,[period]
           ,[frequency]
           ,[notes])
     VALUES
           (1
           ,'喂食'
           ,'item.png'
		   ,1
           ,1
           ,1
           ,'')
GO

INSERT INTO [dbo].[T_CARE_ITEM]
           ([gero_id]
           ,[name]
           ,[icon]
           ,[level]
           ,[period]
           ,[frequency]
           ,[notes])
     VALUES
           (1
           ,'更换尿布'
           ,'item.png'
		   ,1
           ,1
           ,1
           ,'')
GO

INSERT INTO [dbo].[T_CARE_ITEM]
           ([gero_id]
           ,[name]
           ,[icon]
           ,[level]
           ,[period]
           ,[frequency]
           ,[notes])
     VALUES
           (1
           ,'衣物洗涤'
           ,'item.png'
		   ,1
           ,1
           ,1
           ,'')
GO

INSERT INTO [dbo].[T_CARE_ITEM]
           ([gero_id]
           ,[name]
           ,[icon]
           ,[level]
           ,[period]
           ,[frequency]
           ,[notes])
     VALUES
           (1
           ,'尿布洗涤'
           ,'item.png'
		   ,1
           ,1
           ,1
           ,'')
GO

INSERT INTO [dbo].[T_CARE_ITEM]
           ([gero_id]
           ,[name]
           ,[icon]
           ,[level]
           ,[period]
           ,[frequency]
           ,[notes])
     VALUES
           (1
           ,'食物粉碎'
           ,'item.png'
		   ,1
           ,1
           ,1
           ,'')
GO

---------------------------------------------------------------------------------------

-- 生成超管角色

INSERT INTO [dbo].[T_ROLE]
           ([name]
           ,[notes]
		   ,[gero_id])
     VALUES
           ('超级管理员'
           ,'系统超级管理员'
		   ,'1')
GO

---------------------------------------------------------------------------------------

-- 生成养老院默认角色


INSERT INTO [dbo].[T_ROLE]
           ([name]
           ,[notes]
		   ,[gero_id])
     VALUES
           ('管理员'
           ,'管理员'
		   ,'1')
GO

INSERT INTO [dbo].[T_ROLE]
           ([name]
           ,[notes]
		   ,[gero_id])
     VALUES
           ('老人护工'
           ,'老人护工'
		   ,'1')
GO

INSERT INTO [dbo].[T_ROLE]
           ([name]
           ,[notes]
		   ,[gero_id])
     VALUES
           ('房间护工'
           ,'房间护工'
		   ,'1')
GO

INSERT INTO [dbo].[T_ROLE]
           ([name]
           ,[notes]
		   ,[gero_id])
     VALUES
           ('医生'
           ,'医生'
		   ,'1')
GO

INSERT INTO [dbo].[T_ROLE]
           ([name]
           ,[notes]
		   ,[gero_id])
     VALUES
           ('老人'
           ,'老人'
		   ,'1')
GO

INSERT INTO [dbo].[T_ROLE]
           ([name]
           ,[notes]
		   ,[gero_id])
     VALUES
           ('家属'
           ,'家属'
		   ,'1')
GO

---------------------------------------------------------------------------------------

-- 生成超级管理员

INSERT INTO [dbo].[T_USER]([username],[name],[password],[user_type],[user_id],[register_date],[identity_no],[birthday],[phone_no],[gero_id])
     VALUES('su','超级管理员','02a3f0772fcca9f415adc990734b45c6f059c7d33ee28362c4852032','0','0','2015-03-05 00:00:00.000','1','1970-01-01','18888888888','1')
GO

---------------------------------------------------------------------------------------

-- 生成默认养老院管理员

INSERT INTO [dbo].[T_USER]([username],[name],[password],[user_type],[user_id],[register_date],[identity_no],[birthday],[phone_no],[gero_id])
     VALUES('default_admin','默认管理员','02a3f0772fcca9f415adc990734b45c6f059c7d33ee28362c4852032','1','1','2015-03-05 00:00:00.000','1','1970-01-01','18888888888','1')
GO

INSERT INTO [dbo].[T_STAFF]([name],[gero_id])
     VALUES('默认管理员',1)
GO

---------------------------------------------------------------------------------------

-- 生成默认养老院职工

-- 生成默认老人护工

INSERT INTO [dbo].[T_USER]([username],[name],[password],[user_type],[user_id],[register_date],[identity_no],[birthday],[phone_no],[gero_id])
     VALUES('default_elder_carer','默认老人护工','02a3f0772fcca9f415adc990734b45c6f059c7d33ee28362c4852032','1','2','2015-03-05 00:00:00.000','1','1970-01-01','18888888888','1')
GO

INSERT INTO [dbo].[T_STAFF]([name],[gero_id])
     VALUES('默认老人护工',1)
GO

-- 生成默认房间护工

INSERT INTO [dbo].[T_USER]([username],[name],[password],[user_type],[user_id],[register_date],[identity_no],[birthday],[phone_no],[gero_id])
     VALUES('default_room_carer','默认房间护工','02a3f0772fcca9f415adc990734b45c6f059c7d33ee28362c4852032','1','3','2015-03-05 00:00:00.000','1','1970-01-01','18888888888','1')
GO

INSERT INTO [dbo].[T_STAFF]([name],[gero_id])
     VALUES('默认房间护工',1)
GO

-- 生成默认医生

INSERT INTO [dbo].[T_USER]([username],[name],[password],[user_type],[user_id],[register_date],[identity_no],[birthday],[phone_no],[gero_id])
     VALUES('default_doctor','默认医生','02a3f0772fcca9f415adc990734b45c6f059c7d33ee28362c4852032','1','4','2015-03-05 00:00:00.000','1','1970-01-01','18888888888','1')
GO

INSERT INTO [dbo].[T_STAFF]([name],[gero_id])
     VALUES('默认医生',1)
GO

---------------------------------------------------------------------------------------

-- 生成养老院默认老人、家属

-- 生成默认老人

INSERT INTO [dbo].[T_USER]([username],[name],[password],[user_type],[user_id],[register_date],[identity_no],[birthday],[phone_no],[gero_id])
     VALUES('elder','默认老人','02a3f0772fcca9f415adc990734b45c6f059c7d33ee28362c4852032','2','1','2015-03-05 00:00:00.000','1','1970-01-01','18888888888','1')
GO

INSERT INTO [dbo].[T_ELDER]([name],[gero_id],[area_id])
     VALUES('默认老人',1,4)
GO

-- 生成默认家属

INSERT INTO [dbo].[T_USER]([username],[name],[password],[user_type],[user_id],[register_date],[identity_no],[birthday],[phone_no],[gero_id])
     VALUES('relative','默认家属','02a3f0772fcca9f415adc990734b45c6f059c7d33ee28362c4852032','3','1','2015-03-05 00:00:00.000','1','1970-01-01','18888888888','1')
GO

INSERT INTO [dbo].[T_ELDER_RELATIVE]([name],[elder_id])
     VALUES('默认家属',1)
GO

---------------------------------------------------------------------------------------

-- 为用户赋予角色

INSERT INTO [dbo].[T_USER_ROLES]
           ([user_id]
           ,[role_id])
     VALUES
           ('1'
           ,'1'
		   )
GO

INSERT INTO [dbo].[T_USER_ROLES]
           ([user_id]
           ,[role_id])
     VALUES
           ('2'
           ,'2'
		   )
GO

INSERT INTO [dbo].[T_USER_ROLES]
           ([user_id]
           ,[role_id])
     VALUES
           ('3'
           ,'3'
		   )
GO

INSERT INTO [dbo].[T_USER_ROLES]
           ([user_id]
           ,[role_id])
     VALUES
           ('4'
           ,'4'
		   )
GO

INSERT INTO [dbo].[T_USER_ROLES]
           ([user_id]
           ,[role_id])
     VALUES
           ('5'
           ,'5'
		   )
GO

INSERT INTO [dbo].[T_USER_ROLES]
           ([user_id]
           ,[role_id])
     VALUES
           ('6'
           ,'6'
		   )
GO

INSERT INTO [dbo].[T_USER_ROLES]
           ([user_id]
           ,[role_id])
     VALUES
           ('7'
           ,'7'
		   )
GO

---------------------------------------------------------------------------------------

INSERT INTO [dbo].[T_ELDER_TEMPERATURE]
           ([elder_id]
           ,[doctor_id]
           ,[temperature]
           ,[time])
     VALUES
           (1
           ,1
           ,36.6
           ,'2015-03-06')
GO

INSERT INTO [dbo].[T_ELDER_TEMPERATURE]
           ([elder_id]
           ,[doctor_id]
           ,[temperature]
           ,[time])
     VALUES
           (1
           ,1
           ,37.6
           ,'2015-03-05')
GO