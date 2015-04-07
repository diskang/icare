EXEC msdb.dbo.sp_delete_database_backuphistory @database_name = N'HCDB_2'
GO
USE [master]
GO
ALTER DATABASE [HCDB_2] SET  SINGLE_USER WITH ROLLBACK IMMEDIATE
GO
USE [master]
GO
/****** Object:  Database [HCDB_2]    Script Date: 2015/3/13 11:00:40 ******/
DROP DATABASE [HCDB_2]
GO


CREATE DATABASE HCDB_2
GO

USE HCDB_2
GO

CREATE TABLE T_USER
(
	id					int				PRIMARY KEY IDENTITY,	--用户id
	username			nvarchar(30)	NOT NULL	UNIQUE,		--用户登录名
	name				nvarchar(20)	NOT NULL,				--用户姓名
	password			varchar(64)		NOT NULL,				--用户密码
	user_type			int				NOT NULL,				--超级管理员=0、管理员=1，员工=2、老人=3、家属=4
	user_id				int				NOT NULL,				-- -1，gero_id, staff_id, elder_id，relative_id
	register_date		datetime		NOT NULL,				--注册日期
	cancel_date			datetime		,						--注销日期
	gender				char(1)			,						--性别（男0，女1）
	photo_url			varchar(256)	,						--照片url
	identity_no			char(18)		NOT NULL,				--身份证号码
	age					int				,						--年龄
	nationality			nvarchar(20)	,						--民族
	marriage			int				,						--0:未婚  1：已婚 2：离异 3:丧偶
	native_place		nvarchar(20)	,						--籍贯
	birthday			date			NOT NULL,				--出生年月日
	political_status	nvarchar(10)	,						--政治面貌
	education			nvarchar(50)	,						--受教育水平
	phone_no			char(20)		NOT NULL,				--联系方式
	zip_code			char(10)		,						--邮编	
	residence_address	nvarchar(50)	,						--户籍地址
	household_address	nvarchar(50)	,						--居住地址
	email				varchar(20)		,						--邮箱地址
	wechat_id			nvarchar(64)	,						--微信账号
	gero_id				int				NOT NULL,				--养老院id，关联GERO
	
	CONSTRAINT uc_UserID UNIQUE (user_type,user_id)
)
GO

CREATE TABLE T_GERO
(
	id					int				PRIMARY KEY IDENTITY,	--养老院id
	name				nvarchar(50)	NOT NULL,				--养老院名称
	city				nvarchar(10)	NOT NULL,				--所在城市
	district			nvarchar(10)	NOT NULL,				--所在行政区
	address				nvarchar(30)	,						--所在地址
	contact				varchar(20)		,						--联系方式，座机或者手机
	licence				varchar(30)		,						--许可证
	scale				int				,						--养老院人数
	care_level			int				,						--养老院能提供的最高的护理等级
	contact_id			int				,						--养老院联系人id，关联staff表
	logo_url			varchar(256)	,						--养老院logo图片的url地址
	photo_url			varchar(256)	,						--养老院封面图片的url地址
	register_date		datetime		NOT NULL,				--注册日期
	cancel_date			datetime		,						--注销日期
)
GO

CREATE TABLE T_AREA
(
	id					int				PRIMARY KEY IDENTITY,	--位置id
	parent_id			int				NOT NULL,				--0表示逻辑上的根节点
	parent_ids			varchar(1000)	NOT NULL,				--父节点列表
	gero_id				int				NOT NULL,				--养老院id，关联GERO
	type				int				NOT NULL,				--楼栋：1，楼层：2，房间：3，床位：4，区域：5
	level				int				NOT NULL,				--每深一层加1
	name				nvarchar(64)	NOT NULL,				--位置名称
	full_name			nvarchar(500)	NOT NULL,				--位置全名
	del_flag			char(1)			NOT NULL	DEFAULT '0'	--默认0，删除1	
)
GO

CREATE TABLE T_DEVICE
(
	id					int				PRIMARY KEY IDENTITY,	--蓝牙设备id
	elder_id			int				NOT NULL,				--老人id，关联ELDER
	area_id				int				NOT NULL,				--房间id，关联AREA
	name				nvarchar(30)	NOT NULL,				--设备名
	device_type_id		int				NOT NULL,				--设备种类id，关联DEVICE_TYPE
	active_mode			varchar(10)		NOT NULL,				--active：正在测量，pause：当前中止，idol：当前空闲（已连接），disconnect：链接断开
)
GO

CREATE TABLE T_DEVICE_TYPE
(
	id					int				PRIMARY KEY IDENTITY,	--设备种类id
	name				nvarchar(20)	NOT NULL,				--设备种类名
	notes				nvarchar(32)	,						--备注
)
GO

CREATE TABLE T_GERO_ELDER_EXCHANGE
(
	id					int				PRIMARY KEY IDENTITY,	--院属交流信息id
	participants		nvarchar(200)	NOT NULL,				--事件相关人员，用json字符串
	mediators			nvarchar(200)	NOT NULL,				--调解人，用json字符串
	description			ntext			,						--描述
	result				nvarchar(200)	NOT NULL,				--调解结果
	recorder			int				NOT NULL,				--员工id，关联STAFF表
	time				datetime		NOT NULL,				--记录时间
	gero_id				int				NOT NULL,				--养老院id，关联GERO
)

CREATE TABLE T_ELDER
(
	id					int				PRIMARY KEY IDENTITY,	--老人ID
	name				nvarchar(20) 	NOT NULL,				--老人姓名
	gero_id				int				NOT NULL,				--养老院id，关联GERO
	nssf_id				varchar(50)		,						--老人社保卡号
	archive_id			varchar(20)		,						--档案编号，不清楚用处，养老院要求加的。
	area_id				int				,						--老人入住床号，关联T_AREA表
	care_level			int				,						--老人护理等级
	checkin_date		date			,						--入院日期
	checkout_date		date			,						--离院日期
	apply_url			varchar(256)	,						--申请表url（图片）
	survey_url			varchar(256)	,						--调防表url（图片）
	assess_url			varchar(256)	,						--审批表url（图片）
	track_url			varchar(256)	,						--7天跟踪记录表url（图片）
	pad_mac				varchar(17)		,						--老人房间pad的mac，用于绑定上传信息
)
GO

CREATE TABLE T_ELDER_SHEET
(
	elder_id			int				PRIMARY KEY IDENTITY,	--关联T_ELDER表
	apply_name			nvarchar(20)	,						--申请人姓名
	apply_date			date			,						--老人申请入住日期
	medical_history		ntext			,						--既往病史
	apply_reason		nvarchar(50)	,						--申请原因
	surveyor_signature	nvarchar(20)	NOT NULL,				--调防人姓名
	surveyor_role		nvarchar(20)	,						--调访人职位
	survey_date			date			,						--调访日期
	survey_result		bit				NOT NULL,				--调访结论, 1：agree 0：disagree
	survey_special_item	nvarchar(1000)	,						--特殊服务项目内容TODO
	test_result			nvarchar(1000)	NOT NULL,				--体检结论
	doctor_signature	nvarchar(20)	,						--见表T_STAFF
	assess_care_level	int				NOT NULL,				--0专护,1一级,2同理,3.同理。医生评定的护理等级
	assess_signature	nvarchar(20)	,						--评估人签名
	admin_decision		bit				NOT NULL,				--0:不同意，1：同
	register_no			int				,						--住院号，不知道什么用，先留着
	approve_signature	nvarchar(20)							--院长签名
)
GO

CREATE TABLE T_ELDER_RELATIVE
(
	id					int				PRIMARY KEY IDENTITY,	--家属ID
	elder_id			int				NOT NULL,				--关联T_ELDER表
	name				nvarchar(20)	NOT NULL,				--名字
	urgent				bit				,						--是否紧急联系人
	relationship		nvarchar(20)	,						--与老人关系，optional
	cancel_date			datetime		,						--注销日期
)
GO

CREATE TABLE T_SELFCARE_ITEM
(
	id					int				PRIMARY KEY IDENTITY,
	item_name			nvarchar(20)	NOT NULL,				--
	gero_id				int				NOT NULL,				--
)
GO

CREATE TABLE T_ELDER_SELFCARE_STATUS
(
	id					int				PRIMARY KEY IDENTITY,
	elder_id			int				NOT NULL,				--
	selfcare_item_id	int				NOT NULL,				--
	level				int				NOT NULL,				--
)
GO

CREATE TABLE T_ELDER_TEMPERATURE
(
	id					int				PRIMARY KEY IDENTITY,
	elder_id			int				NOT NULL,				--
	doctor_id			int				NOT NULL,				--
	temperature			float			NOT NULL,				--
	time				datetime		NOT NULL,				--
)

CREATE TABLE T_ELDER_HEART_RATE
(
	id					int				PRIMARY KEY IDENTITY,
	elder_id			int				NOT NULL,				--
	doctor_id			int				NOT NULL,				--
	rate				float			NOT NULL,				--
	time				datetime		NOT NULL,				--
)

CREATE TABLE T_ELDER_BLOOD_PRESSURE
(
	id					int				PRIMARY KEY IDENTITY,
	elder_id			int				NOT NULL,				--
	doctor_id			int				NOT NULL,				--
	diastolic_pressure	float			NOT NULL,				--
	systolic_pressure	float			NOT NULL,				--
	time				datetime		NOT NULL,				--
)

CREATE TABLE T_STAFF
(
	id					int				PRIMARY KEY IDENTITY,
	name				nvarchar(20)	NOT NULL,				--
	nssf_id				varchar(20)		,						--社保卡账号
	gero_id				int				NOT NULL,				--关联T_GERO表
	basic_url			varchar(50)		,						--员工基本信息表扫描件的地址
	leave_date			date			,						--离职时间
	archive_id			varchar(20)		,						--纸质档案编号
)
GO

CREATE TABLE T_CAREWORK_RECORD
(
	id					int				PRIMARY KEY IDENTITY,
	carer_id			int				NOT NULL,				--关联T_STAFF表
	elder_id			int				NOT NULL,				--关联T_ELDER表
	elder_item_id		int				NOT NULL,				--关联T_ELDER_ITEM表
	item_name			nvarchar(32)	NOT NULL,				--项目名
	finish_time			datetime		NOT NULL,				--完成时间
)
GO

CREATE TABLE T_AREAWORK_RECORD
(
	id					int				PRIMARY KEY IDENTITY,
	carer_id			int				NOT NULL,				--
	area_item_id		int				NOT NULL,				--
	item_name			nvarchar(32)	NOT NULL,				--项目名
	area_id				int				NOT NULL,				--
	finish_time			datetime		NOT NULL,				--
)
GO

-- CREATE TABLE T_CAREWORK_ELDER_RECORD
-- (
-- 	id					int				PRIMARY KEY IDENTITY,
-- 	carer_id			int				NOT NULL,				--
-- 	elder_id			int				NOT NULL,				--
-- 	elder_item_list		nvarchar(256)	NOT NULL,				--
-- 	finish_time			datetime		NOT NULL,				--
-- )
-- GO

CREATE TABLE T_ELDER_AUDIO_RECORD
(
	id					int				PRIMARY KEY IDENTITY,
	recorder_identity	int				NOT NULL,				--
	recorder_id			int				NOT NULL,				--
	listener_identity	int				NOT NULL,				--
	listener_id			int				NOT NULL,				--
	elder_id			int				NOT NULL,				--
	record_time			datetime		NOT NULL,				--
	url					nvarchar(256)	NOT NULL,				--
	read_times			int				NOT NULL,				--
)
GO

CREATE TABLE T_STAFF_SCHEDULE_PLAN
(
	id					int				PRIMARY KEY IDENTITY,
	staff_id			int				NOT NULL,				--
	gero_id				int				NOT NULL,				--
	work_date			date			NOT NULL,				--
)
GO

-- CREATE TABLE T_CAREWORK_SCHEDULE_DETAIL
--CREATE TABLE T_CAREWORK
--(
--	id					int				PRIMARY KEY IDENTITY,
--	carer_id			int				NOT NULL,				--
--	elder_id			int				NOT NULL,				--
--	start_date			date			NOT NULL,				--
--	end_date			date			NOT NULL,				--
--)
--GO

CREATE TABLE T_CAREWORK
(
	id					int				PRIMARY KEY IDENTITY,
	carer_id			int				NOT NULL,				--护工id
	elder_ids			varchar(1000)	NOT NULL,				--负责老人id列表(,开始,以逗号分割)
	gero_id				int				NOT NULL,
	end_date			date			,						--结束日期
	status				int				NOT NULL,				--状态（按顺序增加）
)
GO


-- CREATE TABLE T_AREAWORK_SCHEDULE_DETAIL
-- CREATE TABLE T_AREAWORK
-- (
	-- id					int				PRIMARY KEY IDENTITY,
	-- carer_id			int				NOT NULL,				--
	-- area_id				int				NOT NULL,				--
	-- start_date			date			NOT NULL,				--
	-- end_date			date			NOT NULL,				--
-- )
-- GO

CREATE TABLE T_AREAWORK
(
	id					int				PRIMARY KEY IDENTITY,
	carer_id			int				NOT NULL,				--护工id
	area_ids			varchar(1000)	NOT NULL,				--负责区域id列表(,开始,以逗号分割)
	gero_id				int				NOT NULL,
	end_date			date			,						--结束日期
	status				int				NOT NULL,				--状态（按顺序增加）
)
GO

CREATE TABLE T_ROLE
(
	id					int				PRIMARY KEY IDENTITY,
	gero_id				int				NOT NULL,				--关联T_GERO，如果为1，表明是系统角色，不能删除
	name				nvarchar(50)	NOT NULL,				--角色名称
	notes				nvarchar(32)	NOT NULL,				--备注
)
GO

CREATE TABLE T_PRIVILEGE
(
	id					int				PRIMARY KEY IDENTITY,
	name				nvarchar(50)	NOT NULL,				--权限名
	parent_id			int				NOT NULL,				--父亲结点，顶级菜单id为1，其父节点为虚拟结点，为0
	parent_ids			varchar(1000)	NOT NULL,				--所有父亲权限列表，用逗号分隔，从0开始。添加到索引
	permission			varchar(255)	,						--shiro权限字符串
	api					varchar(255)	,						--api
	href				varchar(255)	,						--链接
	icon				varchar(100)	,						--图标
	notes				nvarchar(500)	,						--权限说明
)
GO

CREATE TABLE T_ROLE_PRIVILEGES
(
	id					int				PRIMARY KEY IDENTITY,
	role_id				int				NOT NULL,				--
	privilege_id		int				,						--
)
GO

CREATE TABLE T_USER_ROLES
(
	id					int				PRIMARY KEY IDENTITY,
	user_id				int				NOT NULL,				--
	role_id				int				NOT NULL,				--
)
GO

-- CREATE TABLE T_CARE_ITEM
-- (
-- 	id					int				PRIMARY KEY IDENTITY,
-- 	name				nvarchar(32)	NOT NULL,				--
-- 	level				int				NOT NULL,				--
-- 	notes				nvarchar(32)	,						--
-- 	del_flag			char(1)			NOT NULL	DEFAULT '0'	--默认0，删除1	
-- )
-- GO

-- CREATE TABLE T_AREA_ITEM
-- (
-- 	id					int				PRIMARY KEY IDENTITY,
-- 	name				nvarchar(32)	NOT NULL,				--
-- 	notes				nvarchar(32)	,						--
-- 	del_flag			char(1)			NOT NULL	DEFAULT '0'	--默认0，删除1	
-- )
-- GO

CREATE TABLE T_CARE_ITEM
(
	id					int				PRIMARY KEY IDENTITY,	--
	gero_id				int				NOT NULL,				--gero_id=1时为默认项目
	name				nvarchar(32)	NOT NULL,				--
	icon				varchar(32)		,						--
	level				int				NOT NULL,				--
	period				int				,						--
	frequency			int				,						--
	start_time			time(0)			,						--
	end_time			time(0)			,						--
	notes				nvarchar(32)	,						--
	del_flag			char(1)			NOT NULL	DEFAULT '0'	--默认0，删除1	
)
GO

CREATE TABLE T_AREA_ITEM
(
	id					int				PRIMARY KEY IDENTITY,
	gero_id				int				NOT NULL,				--gero_id=1时为默认项目
	name				nvarchar(32)	NOT NULL,				--
	icon				varchar(32)		,						--
	period				int				,						--
	frequency			int				,						--
	notes				nvarchar(32)	,						--
	del_flag			char(1)			NOT NULL	DEFAULT '0'	--默认0，删除1	
)
GO

CREATE TABLE T_ELDER_ITEM
(
	id					int				PRIMARY KEY IDENTITY,
	elder_id			int				NOT NULL,				--
	care_item_id		int				NOT NULL,				--关联T_CARE_ITEM表
	icon				varchar(32)		,						--
	level				int				NOT NULL,				--
	period				int				,						--
	start_time			time(0)			,						--
	end_time			time(0)			,						--
	del_flag			char(1)			NOT NULL	DEFAULT '0'	--默认0，删除1	
)
GO

ALTER TABLE T_GERO
ADD CONSTRAINT fk_GERO_staff_id
FOREIGN KEY (contact_id)
REFERENCES T_STAFF(id)
GO

ALTER TABLE T_USER
ADD CONSTRAINT fk_USER_gero_id
FOREIGN KEY (gero_id)
REFERENCES T_GERO(id)
GO

ALTER TABLE T_AREA
ADD CONSTRAINT fk_AREA_gero_id
FOREIGN KEY (gero_id)
REFERENCES T_GERO(id)
GO

ALTER TABLE T_DEVICE
ADD CONSTRAINT fk_DEVICE_elder_id
FOREIGN KEY (elder_id)
REFERENCES T_ELDER(id)
GO

ALTER TABLE T_DEVICE
ADD CONSTRAINT fk_DEVICE_area_id
FOREIGN KEY (area_id)
REFERENCES T_AREA(id)
GO

ALTER TABLE T_DEVICE
ADD CONSTRAINT fk_DEVICE_type_id
FOREIGN KEY (device_type_id)
REFERENCES T_DEVICE_TYPE(id)
GO

ALTER TABLE T_GERO_ELDER_EXCHANGE
ADD CONSTRAINT fk_GERO_ELDER_EXCHANGE_recorder
FOREIGN KEY (recorder)
REFERENCES T_STAFF(id)
GO

ALTER TABLE T_GERO_ELDER_EXCHANGE
ADD CONSTRAINT fk_GERO_ELDER_EXCHANGE_gero_id
FOREIGN KEY (gero_id)
REFERENCES T_GERO(id)
GO

ALTER TABLE T_ELDER
ADD CONSTRAINT fk_ELDER_gero_id
FOREIGN KEY (gero_id)
REFERENCES T_GERO(id)
GO

ALTER TABLE T_ELDER
ADD CONSTRAINT fk_ELDER_area_id
FOREIGN KEY (area_id)
REFERENCES T_AREA(id)
GO

ALTER TABLE T_ELDER_SHEET
ADD CONSTRAINT fk_ELDER_SHEET_bed_id
FOREIGN KEY (elder_id)
REFERENCES T_ELDER(id)
GO

ALTER TABLE T_ELDER_RELATIVE
ADD CONSTRAINT fk_ELDER_RELATIVE_elder_id
FOREIGN KEY (elder_id)
REFERENCES T_ELDER(id)
GO

ALTER TABLE T_SELFCARE_ITEM
ADD CONSTRAINT fk_SELFCARE_ITEM_gero_id
FOREIGN KEY (gero_id)
REFERENCES T_GERO(id)
GO

ALTER TABLE T_ELDER_SELFCARE_STATUS
ADD CONSTRAINT fk_ELDER_SELFCARE_STATUS_elder_id
FOREIGN KEY (elder_id)
REFERENCES T_ELDER(id)
GO

ALTER TABLE T_ELDER_SELFCARE_STATUS
ADD CONSTRAINT fk_ELDER_SELFCARE_STATUS_selfcare_item_id
FOREIGN KEY (selfcare_item_id)
REFERENCES T_SELFCARE_ITEM(id)
GO

ALTER TABLE T_ELDER_TEMPERATURE
ADD CONSTRAINT fk_ELDER_TEMPERATURE_elder_id
FOREIGN KEY (elder_id)
REFERENCES T_ELDER(id)
GO

ALTER TABLE T_ELDER_TEMPERATURE
ADD CONSTRAINT fk_ELDER_TEMPERATURE_doctor_id
FOREIGN KEY (doctor_id)
REFERENCES T_STAFF(id)
GO

ALTER TABLE T_ELDER_HEART_RATE
ADD CONSTRAINT fk_ELDER_HEART_RATE_elder_id
FOREIGN KEY (elder_id)
REFERENCES T_ELDER(id)
GO

ALTER TABLE T_ELDER_HEART_RATE
ADD CONSTRAINT fk_ELDER_HEART_RATE_doctor_id
FOREIGN KEY (doctor_id)
REFERENCES T_STAFF(id)
GO

ALTER TABLE T_ELDER_BLOOD_PRESSURE
ADD CONSTRAINT fk_ELDER_BLOOD_PRESSURE_elder_id
FOREIGN KEY (elder_id)
REFERENCES T_ELDER(id)
GO

ALTER TABLE T_ELDER_BLOOD_PRESSURE
ADD CONSTRAINT fk_ELDER_BLOOD_PRESSURE_doctor_id
FOREIGN KEY (doctor_id)
REFERENCES T_STAFF(id)
GO

ALTER TABLE T_STAFF
ADD CONSTRAINT fk_STAFF_gero_id
FOREIGN KEY (gero_id)
REFERENCES T_GERO(id)
GO

ALTER TABLE T_CAREWORK_RECORD
ADD CONSTRAINT fk_CAREWORK_RECORD_carer_id
FOREIGN KEY (carer_id)
REFERENCES T_STAFF(id)
GO

ALTER TABLE T_CAREWORK_RECORD
ADD CONSTRAINT fk_CAREWORK_RECORD_elder_item_id
FOREIGN KEY (elder_item_id)
REFERENCES T_ELDER_ITEM(id)
GO

ALTER TABLE T_AREAWORK_RECORD
ADD CONSTRAINT fk_AREAWORK_RECORD_carer_id
FOREIGN KEY (carer_id)
REFERENCES T_STAFF(id)
GO

ALTER TABLE T_AREAWORK_RECORD
ADD CONSTRAINT fk_AREAWORK_RECORD_area_item_id
FOREIGN KEY (area_item_id)
REFERENCES T_AREA_ITEM(id)
GO

ALTER TABLE T_AREAWORK_RECORD
ADD CONSTRAINT fk_AREAWORK_RECORD_area_id
FOREIGN KEY (area_id)
REFERENCES T_AREA(id)
GO

-- ALTER TABLE T_CAREWORK_ELDER_RECORD
-- ADD CONSTRAINT fk_CAREWORK_ELDER_RECORD_carer_id
-- FOREIGN KEY (carer_id)
-- REFERENCES T_STAFF(id)
-- GO

-- ALTER TABLE T_CAREWORK_ELDER_RECORD
-- ADD CONSTRAINT fk_CAREWORK_ELDER_RECORD_elder_id
-- FOREIGN KEY (elder_id)
-- REFERENCES T_ELDER(id)
-- GO

ALTER TABLE T_ELDER_AUDIO_RECORD
ADD CONSTRAINT fk_ELDER_AUDIO_RECORD_elder_id
FOREIGN KEY (elder_id)
REFERENCES T_ELDER(id)
GO

ALTER TABLE T_STAFF_SCHEDULE_PLAN
ADD CONSTRAINT fk_STAFF_SCHEDULE_PLAN_carer_id
FOREIGN KEY (staff_id)
REFERENCES T_STAFF(id)
GO

ALTER TABLE T_STAFF_SCHEDULE_PLAN
ADD CONSTRAINT fk_STAFF_SCHEDULE_PLAN_gero_id
FOREIGN KEY (gero_id)
REFERENCES T_GERO(id)
GO

ALTER TABLE T_CAREWORK
ADD CONSTRAINT fk_CAREWORK_carer_id
FOREIGN KEY (carer_id)
REFERENCES T_STAFF(id)
GO

ALTER TABLE T_CAREWORK
ADD CONSTRAINT fk_CAREWORK_elder_id
FOREIGN KEY (gero_id)
REFERENCES T_GERO(id)
GO

ALTER TABLE T_AREAWORK
ADD CONSTRAINT fk_AREAWORK_carer_id
FOREIGN KEY (carer_id)
REFERENCES T_STAFF(id)
GO

ALTER TABLE T_AREAWORK
ADD CONSTRAINT fk_AREAWORK_area_id
FOREIGN KEY (gero_id)
REFERENCES T_GERO(id)
GO

ALTER TABLE T_ROLE
ADD CONSTRAINT fk_ROLE_gero_id
FOREIGN KEY (gero_id)
REFERENCES T_GERO(id)
GO

ALTER TABLE T_ROLE_PRIVILEGES
ADD CONSTRAINT fk_ROLE_PRIVILEGES_role_id
FOREIGN KEY (role_id)
REFERENCES T_ROLE(id)
ON DELETE CASCADE
GO

ALTER TABLE T_ROLE_PRIVILEGES
ADD CONSTRAINT fk_ROLE_PRIVILEGES_privilege_id
FOREIGN KEY (privilege_id)
REFERENCES T_PRIVILEGE(id)
ON DELETE CASCADE
GO

ALTER TABLE T_USER_ROLES
ADD CONSTRAINT fk_USER_ROLES_staff_id
FOREIGN KEY (user_id)
REFERENCES T_USER(id)
ON DELETE CASCADE
GO

ALTER TABLE T_USER_ROLES
ADD CONSTRAINT fk_USER_ROLES_role_id
FOREIGN KEY (role_id)
REFERENCES T_ROLE(id)
ON DELETE CASCADE
GO

ALTER TABLE T_CARE_ITEM
ADD CONSTRAINT fk_CARE_ITEM_gero_id
FOREIGN KEY (gero_id)
REFERENCES T_GERO(id)
GO

-- ALTER TABLE T_GERO_CARE_ITEM
-- ADD CONSTRAINT fk_GERO_CARE_ITEM_care_item_id
-- FOREIGN KEY (care_item_id)
-- REFERENCES T_CARE_ITEM(id)
-- GO

ALTER TABLE T_AREA_ITEM
ADD CONSTRAINT fk_AREA_ITEM_gero_id
FOREIGN KEY (gero_id)
REFERENCES T_GERO(id)
GO

-- ALTER TABLE T_GERO_AREA_ITEM
-- ADD CONSTRAINT fk_GERO_AREA_ITEM_area_item_id
-- FOREIGN KEY (area_item_id)
-- REFERENCES T_AREA_ITEM(id)
-- GO

ALTER TABLE T_ELDER_ITEM
ADD CONSTRAINT fk_ELDER_ITEM_elder_id
FOREIGN KEY (elder_id)
REFERENCES T_ELDER(id)
GO

ALTER TABLE T_ELDER_ITEM
ADD CONSTRAINT fk_ELDER_ITEM_care_item_id
FOREIGN KEY (care_item_id)
REFERENCES T_CARE_ITEM(id)
GO




---------------------------------------------------------------------


---------------------------------------------------------------------





USE [HCDB_2]
GO

-- 建立默认养老院

INSERT INTO [dbo].[T_GERO]([name],[city],[district],[register_date])
     VALUES('default','default','default','2015-03-12')
GO

-- 建立权限表

INSERT INTO HCDB_2.dbo.T_PRIVILEGE (name,parent_id,parent_ids,permission,api,href,icon,notes) VALUES (
'root',0,'0,','','','','','');
INSERT INTO HCDB_2.dbo.T_PRIVILEGE (name,parent_id,parent_ids,permission,api,href,icon,notes) VALUES (
'用户',1,'0,1','','','','','');
INSERT INTO HCDB_2.dbo.T_PRIVILEGE (name,parent_id,parent_ids,permission,api,href,icon,notes) VALUES (
'用户个人信息',2,'0,1,2','','','','','');
INSERT INTO HCDB_2.dbo.T_PRIVILEGE (name,parent_id,parent_ids,permission,api,href,icon,notes) VALUES (
'查看当前用户信息',3,'0,1,2,3','','','','','');
INSERT INTO HCDB_2.dbo.T_PRIVILEGE (name,parent_id,parent_ids,permission,api,href,icon,notes) VALUES (
'查看当前超管信息',4,'0,1,2,3,4','user:{uid}:info:read','/user/{id}:GET','/user/1','','');
INSERT INTO HCDB_2.dbo.T_PRIVILEGE (name,parent_id,parent_ids,permission,api,href,icon,notes) VALUES (
'查看当前员工信息',4,'0,1,2,3,4','user:{uid}:info:read','/gero/{id}/staff/{id}:GET','','','');
INSERT INTO HCDB_2.dbo.T_PRIVILEGE (name,parent_id,parent_ids,permission,api,href,icon,notes) VALUES (
'查看当前老人信息',4,'0,1,2,3,4','user:{uid}:info:read','/gero/{id}/elder/{id}:GET','','','');
INSERT INTO HCDB_2.dbo.T_PRIVILEGE (name,parent_id,parent_ids,permission,api,href,icon,notes) VALUES (
'查看当前家属信息',4,'0,1,2,3,4','user:{uid}:info:read','','','','');
INSERT INTO HCDB_2.dbo.T_PRIVILEGE (name,parent_id,parent_ids,permission,api,href,icon,notes) VALUES (
'修改当前用户信息',3,'0,1,2,3','','','no','','');
INSERT INTO HCDB_2.dbo.T_PRIVILEGE (name,parent_id,parent_ids,permission,api,href,icon,notes) VALUES (
'修改当前超管信息',9,'0,1,2,3,9','user:{uid}:info:update','/user/{id}:PUT','','','');
INSERT INTO HCDB_2.dbo.T_PRIVILEGE (name,parent_id,parent_ids,permission,api,href,icon,notes) VALUES (
'修改当前员工信息',9,'0,1,2,3,9','user:{uid}:info:update','/gero/{id}/staff/{id}:PUT','','','');
INSERT INTO HCDB_2.dbo.T_PRIVILEGE (name,parent_id,parent_ids,permission,api,href,icon,notes) VALUES (
'修改当前老人信息',9,'0,1,2,3,9','user:{uid}:info:update','/gero/{id}/elder/{id}:PUT','','','');
INSERT INTO HCDB_2.dbo.T_PRIVILEGE (name,parent_id,parent_ids,permission,api,href,icon,notes) VALUES (
'修改当前家属信息',9,'0,1,2,3,9','user:{uid}:info:update','','','','');
INSERT INTO HCDB_2.dbo.T_PRIVILEGE (name,parent_id,parent_ids,permission,api,href,icon,notes) VALUES (
'修改用户密码',3,'0,1,2,3','user:{uid}:password:update','/user/{id}/password:PUT','no','','');
INSERT INTO HCDB_2.dbo.T_PRIVILEGE (name,parent_id,parent_ids,permission,api,href,icon,notes) VALUES (
'老人家属功能模块',2,'0,1,2','','','no','','');
INSERT INTO HCDB_2.dbo.T_PRIVILEGE (name,parent_id,parent_ids,permission,api,href,icon,notes) VALUES (
'查看老人健康信息',15,'0,1,2,15','','','','','');
INSERT INTO HCDB_2.dbo.T_PRIVILEGE (name,parent_id,parent_ids,permission,api,href,icon,notes) VALUES (
'查看老人体温',16,'0,1,2,15,16','elder:{eid}:health:read','/gero/{id}/elder/{id}/temperature:GET','','','');
INSERT INTO HCDB_2.dbo.T_PRIVILEGE (name,parent_id,parent_ids,permission,api,href,icon,notes) VALUES (
'查看老人血压',16,'0,1,2,15,16','elder:{eid}:health:read','/gero/{id}/elder/{id}/blood_pressure:GET','','','');
INSERT INTO HCDB_2.dbo.T_PRIVILEGE (name,parent_id,parent_ids,permission,api,href,icon,notes) VALUES (
'查看老人心律',16,'0,1,2,15,16','elder:{eid}:health:read','/gero/{id}/elder/{id}/heart_rate:GET','','','');
INSERT INTO HCDB_2.dbo.T_PRIVILEGE (name,parent_id,parent_ids,permission,api,href,icon,notes) VALUES (
'查看老人项目记录',15,'0,1,2,15','elder:{eid}:carework_record:read','/gero/{id}/carework_record:GET','','','');
INSERT INTO HCDB_2.dbo.T_PRIVILEGE (name,parent_id,parent_ids,permission,api,href,icon,notes) VALUES (
'查看老人负责护工',15,'0,1,2,15','elder:{eid}:carework:read','/gero/{id}/carework:GET','','','');
INSERT INTO HCDB_2.dbo.T_PRIVILEGE (name,parent_id,parent_ids,permission,api,href,icon,notes) VALUES (
'查看老人项目',15,'0,1,2,15','elder:{eid}:care_item:read','/gero/{id}/elder/{id}/care_item:GET','','','');
INSERT INTO HCDB_2.dbo.T_PRIVILEGE (name,parent_id,parent_ids,permission,api,href,icon,notes) VALUES (
'职工功能模块',2,'0,1,2','','','','','');
INSERT INTO HCDB_2.dbo.T_PRIVILEGE (name,parent_id,parent_ids,permission,api,href,icon,notes) VALUES (
'养老院信息',23,'0,1,2,23','','','','','');
INSERT INTO HCDB_2.dbo.T_PRIVILEGE (name,parent_id,parent_ids,permission,api,href,icon,notes) VALUES (
'查看养老院老人',24,'0,1,2,23,24','staff:{sid}:gero:{gid}:elder:read','/gero/{id}/elder/{id}:GET','/gero/1/elder','','');
INSERT INTO HCDB_2.dbo.T_PRIVILEGE (name,parent_id,parent_ids,permission,api,href,icon,notes) VALUES (
'查看养老院基本信息',24,'0,1,2,23,24','staff:{sid}:gero:{gid}:info:read','/gero/{id}:GET','','','');
INSERT INTO HCDB_2.dbo.T_PRIVILEGE (name,parent_id,parent_ids,permission,api,href,icon,notes) VALUES (
'获取养老院所有的区域列表',24,'0,1,2,23,24','staff:{sid}:gero:{gid}:area:read','/gero/{id}/area:GET','','','');
INSERT INTO HCDB_2.dbo.T_PRIVILEGE (name,parent_id,parent_ids,permission,api,href,icon,notes) VALUES (
'获取某区域及其子区域',24,'0,1,2,23,24','staff:{sid}:gero:{gid}:area:read','/gero/{id}/area/{id}:GET','','','');
INSERT INTO HCDB_2.dbo.T_PRIVILEGE (name,parent_id,parent_ids,permission,api,href,icon,notes) VALUES (
'查看本人排班信息',23,'0,1,2,23','','','no','','');
INSERT INTO HCDB_2.dbo.T_PRIVILEGE (name,parent_id,parent_ids,permission,api,href,icon,notes) VALUES (
'获取某员工的上班信息列表',29,'0,1,2,23,29','staff:{sid}:schedule:read','/gero/{id}/staff/{id}/schedule:GET','','','');
INSERT INTO HCDB_2.dbo.T_PRIVILEGE (name,parent_id,parent_ids,permission,api,href,icon,notes) VALUES (
'获取某员工的某天是否上班',29,'0,1,2,23,29','staff:{sid}:schedule:read','/staff/{id}/schedule/{date}:GET','','','');
INSERT INTO HCDB_2.dbo.T_PRIVILEGE (name,parent_id,parent_ids,permission,api,href,icon,notes) VALUES (
'养老院管理模块',23,'0,1,2,23','','','','','');
INSERT INTO HCDB_2.dbo.T_PRIVILEGE (name,parent_id,parent_ids,permission,api,href,icon,notes) VALUES (
'养老院基本信息',32,'0,1,2,23,32','','','no','','');
INSERT INTO HCDB_2.dbo.T_PRIVILEGE (name,parent_id,parent_ids,permission,api,href,icon,notes) VALUES (
'获取养老院基本信息',33,'0,1,2,23,32,33','admin:gero:{gid}:info:read','/gero/{id}:GET','','','');
INSERT INTO HCDB_2.dbo.T_PRIVILEGE (name,parent_id,parent_ids,permission,api,href,icon,notes) VALUES (
'修改养老院基本信息',33,'0,1,2,23,32,33','admin:gero:{gid}:info:update','/gero/{id}:PUT','','','');
INSERT INTO HCDB_2.dbo.T_PRIVILEGE (name,parent_id,parent_ids,permission,api,href,icon,notes) VALUES (
'区域管理',32,'0,1,2,23,32','','','','','');
INSERT INTO HCDB_2.dbo.T_PRIVILEGE (name,parent_id,parent_ids,permission,api,href,icon,notes) VALUES (
'获取养老院所有的区域列表',36,'0,1,2,23,32,36','admin:gero:{gid}:area:read','/gero/{id}/area:GET','/area','','');
INSERT INTO HCDB_2.dbo.T_PRIVILEGE (name,parent_id,parent_ids,permission,api,href,icon,notes) VALUES (
'创建一个养老院区域',36,'0,1,2,23,32,36','admin:gero:{gid}:area:add','/gero/{id}/area:POST','','','');
INSERT INTO HCDB_2.dbo.T_PRIVILEGE (name,parent_id,parent_ids,permission,api,href,icon,notes) VALUES (
'获取某区域及其子区域',36,'0,1,2,23,32,36','admin:gero:{gid}:area:read','/gero/{id}/area/{id}:GET','','','');
INSERT INTO HCDB_2.dbo.T_PRIVILEGE (name,parent_id,parent_ids,permission,api,href,icon,notes) VALUES (
'修改某区域',36,'0,1,2,23,32,36','admin:gero:{gid}:area:update','/gero/{id}/area/{id}:PUT','','','');
INSERT INTO HCDB_2.dbo.T_PRIVILEGE (name,parent_id,parent_ids,permission,api,href,icon,notes) VALUES (
'删除某区域',36,'0,1,2,23,32,36','admin:gero:{gid}:area:delete','/gero/{id}/area/{id}:DELETE','','','');
INSERT INTO HCDB_2.dbo.T_PRIVILEGE (name,parent_id,parent_ids,permission,api,href,icon,notes) VALUES (
'项目管理',32,'0,1,2,23,32','','','','','');
INSERT INTO HCDB_2.dbo.T_PRIVILEGE (name,parent_id,parent_ids,permission,api,href,icon,notes) VALUES (
'老人项目',42,'0,1,2,23,32,42','','','','','');
INSERT INTO HCDB_2.dbo.T_PRIVILEGE (name,parent_id,parent_ids,permission,api,href,icon,notes) VALUES (
'老人项目列表',43,'0,1,2,23,32,42,43','admin:gero:{gid}:care_item:read','/gero/{id}/care_item:GET','/gero/1/care_item','','');
INSERT INTO HCDB_2.dbo.T_PRIVILEGE (name,parent_id,parent_ids,permission,api,href,icon,notes) VALUES (
'添加老人项目',43,'0,1,2,23,32,42,43','admin:gero:{gid}:care_item:add','/gero/{id}/care_item:POST','','','');
INSERT INTO HCDB_2.dbo.T_PRIVILEGE (name,parent_id,parent_ids,permission,api,href,icon,notes) VALUES (
'取老人项目',43,'0,1,2,23,32,42,43','admin:gero:{gid}:care_item:read','/gero/{id}/care_item/{id}:GET','','','');
INSERT INTO HCDB_2.dbo.T_PRIVILEGE (name,parent_id,parent_ids,permission,api,href,icon,notes) VALUES (
'改老人项目',43,'0,1,2,23,32,42,43','admin:gero:{gid}:care_item:update','/gero/{id}/care_item/{id}:PUT','','','');
INSERT INTO HCDB_2.dbo.T_PRIVILEGE (name,parent_id,parent_ids,permission,api,href,icon,notes) VALUES (
'删老人项目',43,'0,1,2,23,32,42,43','admin:gero:{gid}:care_item:delete','/gero/{id}/care_item/{id}:DELETE','','','');
INSERT INTO HCDB_2.dbo.T_PRIVILEGE (name,parent_id,parent_ids,permission,api,href,icon,notes) VALUES (
'房间项目',42,'0,1,2,23,32,42','','','','','');
INSERT INTO HCDB_2.dbo.T_PRIVILEGE (name,parent_id,parent_ids,permission,api,href,icon,notes) VALUES (
'房间项目列表',49,'0,1,2,23,32,42,49','admin:gero:{gid}:area_item:read','/gero/{id}/area_item:GET','/gero/1/area_item','','');
INSERT INTO HCDB_2.dbo.T_PRIVILEGE (name,parent_id,parent_ids,permission,api,href,icon,notes) VALUES (
'添加房间项目',49,'0,1,2,23,32,42,49','admin:gero:{gid}:area_item:add','/gero/{id}/area_item:POST','','','');
INSERT INTO HCDB_2.dbo.T_PRIVILEGE (name,parent_id,parent_ids,permission,api,href,icon,notes) VALUES (
'取房间项目',49,'0,1,2,23,32,42,49','admin:gero:{gid}:area_item:read','/gero/{id}/area_item/{id}:GET','','','');
INSERT INTO HCDB_2.dbo.T_PRIVILEGE (name,parent_id,parent_ids,permission,api,href,icon,notes) VALUES (
'改房间项目',49,'0,1,2,23,32,42,49','admin:gero:{gid}:area_item:update','/gero/{id}/area_item/{id}:PUT','','','');
INSERT INTO HCDB_2.dbo.T_PRIVILEGE (name,parent_id,parent_ids,permission,api,href,icon,notes) VALUES (
'删房间项目',49,'0,1,2,23,32,42,49','admin:gero:{gid}:area_item:delete','/gero/{id}/area_item/{id}:DELETE','','','');
INSERT INTO HCDB_2.dbo.T_PRIVILEGE (name,parent_id,parent_ids,permission,api,href,icon,notes) VALUES (
'老人专属项目',42,'0,1,2,23,32,42','','','','','');
INSERT INTO HCDB_2.dbo.T_PRIVILEGE (name,parent_id,parent_ids,permission,api,href,icon,notes) VALUES (
'老人专属项目列表',55,'0,1,2,23,32,42,55','admin:gero:{gid}:elder:care_item:read','/gero/{id}/elder/{id}/care_item:GET','/elder_care_item','','');
INSERT INTO HCDB_2.dbo.T_PRIVILEGE (name,parent_id,parent_ids,permission,api,href,icon,notes) VALUES (
'添加老人专属项目',55,'0,1,2,23,32,42,55','admin:gero:{gid}:elder:care_item:add','/gero/{id}/elder/{id}/care_item:POST','','','');
INSERT INTO HCDB_2.dbo.T_PRIVILEGE (name,parent_id,parent_ids,permission,api,href,icon,notes) VALUES (
'取老人专属项目',55,'0,1,2,23,32,42,55','admin:gero:{gid}:elder:care_item:read','/gero/{id}/elder/{id}/care_item/{id}:GET','','','');
INSERT INTO HCDB_2.dbo.T_PRIVILEGE (name,parent_id,parent_ids,permission,api,href,icon,notes) VALUES (
'改老人专属项目',55,'0,1,2,23,32,42,55','admin:gero:{gid}:elder:care_item:update','/gero/{id}/elder/{id}/care_item/{id}:PUT','','','');
INSERT INTO HCDB_2.dbo.T_PRIVILEGE (name,parent_id,parent_ids,permission,api,href,icon,notes) VALUES (
'删老人专属项目',55,'0,1,2,23,32,42,55','admin:gero:{gid}:elder:care_item:delete','/gero/{id}/elder/{id}/care_item/{id}:DELETE','','','');
INSERT INTO HCDB_2.dbo.T_PRIVILEGE (name,parent_id,parent_ids,permission,api,href,icon,notes) VALUES (
'院属互动',32,'0,1,2,23,32','','','no','','');
INSERT INTO HCDB_2.dbo.T_PRIVILEGE (name,parent_id,parent_ids,permission,api,href,icon,notes) VALUES (
'院属互动列表',61,'0,1,2,23,32,61','admin:gero:{gid}:exchange:read','/gero/{id}/exchange:GET','','','');
INSERT INTO HCDB_2.dbo.T_PRIVILEGE (name,parent_id,parent_ids,permission,api,href,icon,notes) VALUES (
'添加院属互动',61,'0,1,2,23,32,61','admin:gero:{gid}:exchange:add','/gero/{id}/exchange:POST','','','');
INSERT INTO HCDB_2.dbo.T_PRIVILEGE (name,parent_id,parent_ids,permission,api,href,icon,notes) VALUES (
'取院属互动',61,'0,1,2,23,32,61','admin:gero:{gid}:exchange:read','/gero/{id}/exchange/{id}:GET','','','');
INSERT INTO HCDB_2.dbo.T_PRIVILEGE (name,parent_id,parent_ids,permission,api,href,icon,notes) VALUES (
'改院属互动',61,'0,1,2,23,32,61','admin:gero:{gid}:exchange:update','/gero/{id}/exchange/{id}:PUT','','','');
INSERT INTO HCDB_2.dbo.T_PRIVILEGE (name,parent_id,parent_ids,permission,api,href,icon,notes) VALUES (
'删院属互动',61,'0,1,2,23,32,61','admin:gero:{gid}:exchange:delete','/gero/{id}/exchange/{id}:DELETE','','','');
INSERT INTO HCDB_2.dbo.T_PRIVILEGE (name,parent_id,parent_ids,permission,api,href,icon,notes) VALUES (
'角色管理',32,'0,1,2,23,32','','','','','');
INSERT INTO HCDB_2.dbo.T_PRIVILEGE (name,parent_id,parent_ids,permission,api,href,icon,notes) VALUES (
'养老院角色列表',67,'0,1,2,23,32,67','admin:gero:{gid}:role:read','/gero/{id}/role:GET','/gero/1/role','','');
INSERT INTO HCDB_2.dbo.T_PRIVILEGE (name,parent_id,parent_ids,permission,api,href,icon,notes) VALUES (
'新增养老院角色',67,'0,1,2,23,32,67','admin:gero:{gid}:role:add','/gero/{id}/role:POST','','','');
INSERT INTO HCDB_2.dbo.T_PRIVILEGE (name,parent_id,parent_ids,permission,api,href,icon,notes) VALUES (
'获取角色信息',67,'0,1,2,23,32,67','admin:gero:{gid}:role:read','/gero/{id}/role/{id}:GET','','','');
INSERT INTO HCDB_2.dbo.T_PRIVILEGE (name,parent_id,parent_ids,permission,api,href,icon,notes) VALUES (
'修改角色的基本信息',67,'0,1,2,23,32,67','admin:gero:{gid}:role:update','/gero/{id}/role/{id}:PUT','','','');
INSERT INTO HCDB_2.dbo.T_PRIVILEGE (name,parent_id,parent_ids,permission,api,href,icon,notes) VALUES (
'删除养老院角色',67,'0,1,2,23,32,67','admin:gero:{gid}:role:delete','/gero/{id}/role/{id}:DELETE','','','');
INSERT INTO HCDB_2.dbo.T_PRIVILEGE (name,parent_id,parent_ids,permission,api,href,icon,notes) VALUES (
'给养老院角色添加权限',67,'0,1,2,23,32,67','admin:gero:{gid}:role:update','/gero/{id}/role/{id}/privilege:POST','','','');
INSERT INTO HCDB_2.dbo.T_PRIVILEGE (name,parent_id,parent_ids,permission,api,href,icon,notes) VALUES (
'给养老院角色删除权限',67,'0,1,2,23,32,67','admin:gero:{gid}:role:update','/gero/{id}/role/{id}/privilege:DELETE','','','');
INSERT INTO HCDB_2.dbo.T_PRIVILEGE (name,parent_id,parent_ids,permission,api,href,icon,notes) VALUES (
'为某个角色关联员工',67,'0,1,2,23,32,67','admin:gero:{gid}:role:update','/gero/{id}/role/{id}/user:PUT','','','');
INSERT INTO HCDB_2.dbo.T_PRIVILEGE (name,parent_id,parent_ids,permission,api,href,icon,notes) VALUES (
'职工基本信息管理',32,'0,1,2,23,32','','','','','');
INSERT INTO HCDB_2.dbo.T_PRIVILEGE (name,parent_id,parent_ids,permission,api,href,icon,notes) VALUES (
'职工基本信息列表',76,'0,1,2,23,32,76','admin:gero:{gid}:staff:info:read','/gero/{id}/staff:GET','/gero/1/staff','','');
INSERT INTO HCDB_2.dbo.T_PRIVILEGE (name,parent_id,parent_ids,permission,api,href,icon,notes) VALUES (
'增加职工基本信息',76,'0,1,2,23,32,76','admin:gero:{gid}:staff:info:add','/gero/{id}/staff:POST','','','');
INSERT INTO HCDB_2.dbo.T_PRIVILEGE (name,parent_id,parent_ids,permission,api,href,icon,notes) VALUES (
'查找职工基本信息',76,'0,1,2,23,32,76','admin:gero:{gid}:staff:info:read','/gero/{id}/staff/{id}:GET','','','');
INSERT INTO HCDB_2.dbo.T_PRIVILEGE (name,parent_id,parent_ids,permission,api,href,icon,notes) VALUES (
'修改职工基本信息',76,'0,1,2,23,32,76','admin:gero:{gid}:staff:info:update','/gero/{id}/staff/{id}:PUT','','','');
INSERT INTO HCDB_2.dbo.T_PRIVILEGE (name,parent_id,parent_ids,permission,api,href,icon,notes) VALUES (
'删除职工基本信息',76,'0,1,2,23,32,76','admin:gero:{gid}:staff:info:delete','/gero/{id}/staff/{id}:DELETE','','','');
INSERT INTO HCDB_2.dbo.T_PRIVILEGE (name,parent_id,parent_ids,permission,api,href,icon,notes) VALUES (
'老人基本信息管理',32,'0,1,2,23,32','','','','','');
INSERT INTO HCDB_2.dbo.T_PRIVILEGE (name,parent_id,parent_ids,permission,api,href,icon,notes) VALUES (
'老人基本信息列表',82,'0,1,2,23,32,82','admin:gero:{gid}:elder:info:read','/gero/{id}/elder:GET',NULL,'','');
INSERT INTO HCDB_2.dbo.T_PRIVILEGE (name,parent_id,parent_ids,permission,api,href,icon,notes) VALUES (
'新增老人基本信息',82,'0,1,2,23,32,82','admin:gero:{gid}:elder:info:add','/gero/{id}/elder:POST','','','');
INSERT INTO HCDB_2.dbo.T_PRIVILEGE (name,parent_id,parent_ids,permission,api,href,icon,notes) VALUES (
'修改老人基本信息',82,'0,1,2,23,32,82','admin:gero:{gid}:elder:info:read','/gero/{id}/elder/{id}:GET','','','');
INSERT INTO HCDB_2.dbo.T_PRIVILEGE (name,parent_id,parent_ids,permission,api,href,icon,notes) VALUES (
'查找老人基本信息',82,'0,1,2,23,32,82','admin:gero:{gid}:elder:info:update','/gero/{id}/elder/{id}:PUT','','','');
INSERT INTO HCDB_2.dbo.T_PRIVILEGE (name,parent_id,parent_ids,permission,api,href,icon,notes) VALUES (
'删除老人基本信息',82,'0,1,2,23,32,82','admin:gero:{gid}:elder:info:delete','/gero/{id}/elder/{id}:DELETE','','','');
INSERT INTO HCDB_2.dbo.T_PRIVILEGE (name,parent_id,parent_ids,permission,api,href,icon,notes) VALUES (
'老人健康信息管理',32,'0,1,2,23,32','','','no','','');
INSERT INTO HCDB_2.dbo.T_PRIVILEGE (name,parent_id,parent_ids,permission,api,href,icon,notes) VALUES (
'获取体温',88,'0,1,2,23,32,88','admin:gero:{gid}:elder:health:read','/gero/{id}/elder/{id}/temperature:GET','','','');
INSERT INTO HCDB_2.dbo.T_PRIVILEGE (name,parent_id,parent_ids,permission,api,href,icon,notes) VALUES (
'新增体温',88,'0,1,2,23,32,88','admin:gero:{gid}:elder:health:add','/gero/{id}/elder/{id}/temperature:POST','','','');
INSERT INTO HCDB_2.dbo.T_PRIVILEGE (name,parent_id,parent_ids,permission,api,href,icon,notes) VALUES (
'获取血压',88,'0,1,2,23,32,88','admin:gero:{gid}:elder:health:read','/gero/{id}/elder/{id}/blood_pressure:GET','','','');
INSERT INTO HCDB_2.dbo.T_PRIVILEGE (name,parent_id,parent_ids,permission,api,href,icon,notes) VALUES (
'新增血压',88,'0,1,2,23,32,88','admin:gero:{gid}:elder:health:add','/gero/{id}/elder/{id}/blood_pressure:POST','','','');
INSERT INTO HCDB_2.dbo.T_PRIVILEGE (name,parent_id,parent_ids,permission,api,href,icon,notes) VALUES (
'获取心率',88,'0,1,2,23,32,88','admin:gero:{gid}:elder:health:read','/gero/{id}/elder/{id}/heart_rate:GET','','','');
INSERT INTO HCDB_2.dbo.T_PRIVILEGE (name,parent_id,parent_ids,permission,api,href,icon,notes) VALUES (
'新增心率',88,'0,1,2,23,32,88','admin:gero:{gid}:elder:health:add','/gero/{id}/elder/{id}/heart_rate:POST','','','');
INSERT INTO HCDB_2.dbo.T_PRIVILEGE (name,parent_id,parent_ids,permission,api,href,icon,notes) VALUES (
'项目记录管理',32,'0,1,2,23,32','','','no','','');
INSERT INTO HCDB_2.dbo.T_PRIVILEGE (name,parent_id,parent_ids,permission,api,href,icon,notes) VALUES (
'老人护工',95,'0,1,2,23,32,95','','','','','');
INSERT INTO HCDB_2.dbo.T_PRIVILEGE (name,parent_id,parent_ids,permission,api,href,icon,notes) VALUES (
'老人护工的项目记录列表',96,'0,1,2,23,32,95,96','admin:gero:{gid}:carework_record:read','/gero/{id}/carework_record:GET','','','');
INSERT INTO HCDB_2.dbo.T_PRIVILEGE (name,parent_id,parent_ids,permission,api,href,icon,notes) VALUES (
'添加老人护工项目记录',96,'0,1,2,23,32,95,96','admin:gero:{gid}:carework_record:add','/gero/{id}/carework_record:POST','','','');
INSERT INTO HCDB_2.dbo.T_PRIVILEGE (name,parent_id,parent_ids,permission,api,href,icon,notes) VALUES (
'房间护工',95,'0,1,2,23,32,95','','','','','');
INSERT INTO HCDB_2.dbo.T_PRIVILEGE (name,parent_id,parent_ids,permission,api,href,icon,notes) VALUES (
'房间护工的项目记录列表',99,'0,1,2,23,32,95,99','admin:gero:{gid}:areawork_record:read','/gero/{id}/areawork_record:GET','','','');
INSERT INTO HCDB_2.dbo.T_PRIVILEGE (name,parent_id,parent_ids,permission,api,href,icon,notes) VALUES (
'添加房间护工项目记录',99,'0,1,2,23,32,95,99','admin:gero:{gid}:areawork_record:add','/gero/{id}/areawork_record:POST','','','');
INSERT INTO HCDB_2.dbo.T_PRIVILEGE (name,parent_id,parent_ids,permission,api,href,icon,notes) VALUES (
'排班管理',32,'0,1,2,23,32','','','','','');
INSERT INTO HCDB_2.dbo.T_PRIVILEGE (name,parent_id,parent_ids,permission,api,href,icon,notes) VALUES (
'上班时间安排',102,'0,1,2,23,32,102','','','','','');
INSERT INTO HCDB_2.dbo.T_PRIVILEGE (name,parent_id,parent_ids,permission,api,href,icon,notes) VALUES (
'上班时间安排列表',103,'0,1,2,23,32,102,103','admin:gero:{gid}:schedule:read','/gero/{id}/schedule:GET','/gero/1/schedule','','');
INSERT INTO HCDB_2.dbo.T_PRIVILEGE (name,parent_id,parent_ids,permission,api,href,icon,notes) VALUES (
'修改上班时间安排',103,'0,1,2,23,32,102,103','admin:gero:{gid}:schedule:update','/gero/{id}/schedule:PUT','','','');
INSERT INTO HCDB_2.dbo.T_PRIVILEGE (name,parent_id,parent_ids,permission,api,href,icon,notes) VALUES (
'老人护工职责',102,'0,1,2,23,32,102','','','','','');
INSERT INTO HCDB_2.dbo.T_PRIVILEGE (name,parent_id,parent_ids,permission,api,href,icon,notes) VALUES (
'老人护工职责列表',106,'0,1,2,23,32,102,106','admin:gero:{gid}:carework:read','/gero/{id}/carework:GET','/eldercareduty','','');
INSERT INTO HCDB_2.dbo.T_PRIVILEGE (name,parent_id,parent_ids,permission,api,href,icon,notes) VALUES (
'新增老人护工职责',106,'0,1,2,23,32,102,106','admin:gero:{gid}:carework:add','/gero/{id}/carework:POST','','','');
INSERT INTO HCDB_2.dbo.T_PRIVILEGE (name,parent_id,parent_ids,permission,api,href,icon,notes) VALUES (
'修改老人护工职责',106,'0,1,2,23,32,102,106','admin:gero:{gid}:carework:update','/gero/{id}/carework/{id}:PUT','','','');
INSERT INTO HCDB_2.dbo.T_PRIVILEGE (name,parent_id,parent_ids,permission,api,href,icon,notes) VALUES (
'删除老人护工职责',106,'0,1,2,23,32,102,106','admin:gero:{gid}:carework:delete','/gero/{id}/carework/{id}:DELETE','','','');
INSERT INTO HCDB_2.dbo.T_PRIVILEGE (name,parent_id,parent_ids,permission,api,href,icon,notes) VALUES (
'房间护工职责',102,'0,1,2,23,32,102','','','','','');
INSERT INTO HCDB_2.dbo.T_PRIVILEGE (name,parent_id,parent_ids,permission,api,href,icon,notes) VALUES (
'房间护工职责列表',111,'0,1,2,23,32,102,111','admin:gero:{gid}:areawork:read','/gero/{id}/areawork:GET','/areacareduty','','');
INSERT INTO HCDB_2.dbo.T_PRIVILEGE (name,parent_id,parent_ids,permission,api,href,icon,notes) VALUES (
'新增房间护工职责',111,'0,1,2,23,32,102,111','admin:gero:{gid}:areawork:add','/gero/{id}/areawork:POST','','','');
INSERT INTO HCDB_2.dbo.T_PRIVILEGE (name,parent_id,parent_ids,permission,api,href,icon,notes) VALUES (
'修改房间护工职责',111,'0,1,2,23,32,102,111','admin:gero:{gid}:areawork:update','/gero/{id}/areawork/{id}:PUT','','','');
INSERT INTO HCDB_2.dbo.T_PRIVILEGE (name,parent_id,parent_ids,permission,api,href,icon,notes) VALUES (
'删除房间护工职责',111,'0,1,2,23,32,102,111','admin:gero:{gid}:areawork:delete','/gero/{id}/areawork/{id}:DELETE','','','');
INSERT INTO HCDB_2.dbo.T_PRIVILEGE (name,parent_id,parent_ids,permission,api,href,icon,notes) VALUES (
'护工模块',23,'0,1,2,23','','','no','','');
INSERT INTO HCDB_2.dbo.T_PRIVILEGE (name,parent_id,parent_ids,permission,api,href,icon,notes) VALUES (
'查看项目',116,'0,1,2,23,116','','','no','','');
INSERT INTO HCDB_2.dbo.T_PRIVILEGE (name,parent_id,parent_ids,permission,api,href,icon,notes) VALUES (
'老人项目列表',117,'0,1,2,23,116,117','carer:{cid}:gero:{gid}:care_item:read','/gero/{id}/care_item:GET','','','');
INSERT INTO HCDB_2.dbo.T_PRIVILEGE (name,parent_id,parent_ids,permission,api,href,icon,notes) VALUES (
'老人专属项目列表',117,'0,1,2,23,116,117','carer:{cid}:gero:{gid}:elder:care_item:read','/gero/{id}/elder/{id}/care_item:GET','','','');
INSERT INTO HCDB_2.dbo.T_PRIVILEGE (name,parent_id,parent_ids,permission,api,href,icon,notes) VALUES (
'房间项目列表',117,'0,1,2,23,116,117','carer:{cid}:gero:{gid}:area_item:read','/gero/{id}/area_item:GET','','','');
INSERT INTO HCDB_2.dbo.T_PRIVILEGE (name,parent_id,parent_ids,permission,api,href,icon,notes) VALUES (
'查询老人项目',117,'0,1,2,23,116,117','carer:{cid}:gero:{gid}:care_item:read','/gero/{id}/care_item/{id}:GET','','','');
INSERT INTO HCDB_2.dbo.T_PRIVILEGE (name,parent_id,parent_ids,permission,api,href,icon,notes) VALUES (
'查询老人专属项目',117,'0,1,2,23,116,117','carer:{cid}:gero:{gid}:elder:care_item:read','/gero/{id}/elder/{id}/care_item/{id}:GET','','','');
INSERT INTO HCDB_2.dbo.T_PRIVILEGE (name,parent_id,parent_ids,permission,api,href,icon,notes) VALUES (
'查询房间项目',117,'0,1,2,23,116,117','carer:{cid}:gero:{gid}:area_item:read','/gero/{id}/area_item/{id}:GET','','','');
INSERT INTO HCDB_2.dbo.T_PRIVILEGE (name,parent_id,parent_ids,permission,api,href,icon,notes) VALUES (
'项目记录',116,'0,1,2,23,116','','','','','');
INSERT INTO HCDB_2.dbo.T_PRIVILEGE (name,parent_id,parent_ids,permission,api,href,icon,notes) VALUES (
'老人护工查看',124,'0,1,2,23,116,124','carer:{cid}:gero:{gid}:carework_record:read','/gero/{id}/carework_record','','','');
INSERT INTO HCDB_2.dbo.T_PRIVILEGE (name,parent_id,parent_ids,permission,api,href,icon,notes) VALUES (
'老人护工新增',124,'0,1,2,23,116,124','carer:{cid}:gero:{gid}:carework_record:add','/gero/{id}/carework_record','','','');
INSERT INTO HCDB_2.dbo.T_PRIVILEGE (name,parent_id,parent_ids,permission,api,href,icon,notes) VALUES (
'房间护工查看',124,'0,1,2,23,116,124','carer:{cid}:gero:{gid}:areawork_record:read','/gero/{id}/areawork_record','','','');
INSERT INTO HCDB_2.dbo.T_PRIVILEGE (name,parent_id,parent_ids,permission,api,href,icon,notes) VALUES (
'房间护工新增',124,'0,1,2,23,116,124','carer:{cid}:gero:{gid}:areawork_record:add','/gero/{id}/areawork_record','','','');
INSERT INTO HCDB_2.dbo.T_PRIVILEGE (name,parent_id,parent_ids,permission,api,href,icon,notes) VALUES (
'查看职责',116,'0,1,2,23,116','','','','','');
INSERT INTO HCDB_2.dbo.T_PRIVILEGE (name,parent_id,parent_ids,permission,api,href,icon,notes) VALUES (
'老人护工',129,'0,1,2,23,116,129','carer:{cid}:gero:{gid}:duty_carer:read','/gero/{id}/elder/{id}/duty_carer','','','');
INSERT INTO HCDB_2.dbo.T_PRIVILEGE (name,parent_id,parent_ids,permission,api,href,icon,notes) VALUES (
'房间护工',129,'0,1,2,23,116,129','carer:{cid}:gero:{gid}:duty_carer:read','/gero/{id}/area/{id}/duty_carer','','','');
INSERT INTO HCDB_2.dbo.T_PRIVILEGE (name,parent_id,parent_ids,permission,api,href,icon,notes) VALUES (
'医生模块',23,'0,1,2,23','','','no','','');
INSERT INTO HCDB_2.dbo.T_PRIVILEGE (name,parent_id,parent_ids,permission,api,href,icon,notes) VALUES (
'老人健康信息管理',132,'0,1,2,23,132','','','','','');
INSERT INTO HCDB_2.dbo.T_PRIVILEGE (name,parent_id,parent_ids,permission,api,href,icon,notes) VALUES (
'获取体温',133,'0,1,2,23,132,133','doctor:gero:{gid}:elder:health:read','/gero/{id}/elder/{id}/temperature:GET','','','');
INSERT INTO HCDB_2.dbo.T_PRIVILEGE (name,parent_id,parent_ids,permission,api,href,icon,notes) VALUES (
'新增体温',133,'0,1,2,23,132,133','doctor:gero:{gid}:elder:health:add','/gero/{id}/elder/{id}/temperature:POST','','','');
INSERT INTO HCDB_2.dbo.T_PRIVILEGE (name,parent_id,parent_ids,permission,api,href,icon,notes) VALUES (
'获取血压',133,'0,1,2,23,132,133','doctor:gero:{gid}:elder:health:read','/gero/{id}/elder/{id}/blood_pressure:GET','','','');
INSERT INTO HCDB_2.dbo.T_PRIVILEGE (name,parent_id,parent_ids,permission,api,href,icon,notes) VALUES (
'新增血压',133,'0,1,2,23,132,133','doctor:gero:{gid}:elder:health:add','/gero/{id}/elder/{id}/blood_pressure:POST','','','');
INSERT INTO HCDB_2.dbo.T_PRIVILEGE (name,parent_id,parent_ids,permission,api,href,icon,notes) VALUES (
'获取心率',133,'0,1,2,23,132,133','doctor:gero:{gid}:elder:health:read','/gero/{id}/elder/{id}/heart_rate:GET','','','');
INSERT INTO HCDB_2.dbo.T_PRIVILEGE (name,parent_id,parent_ids,permission,api,href,icon,notes) VALUES (
'新增心率',133,'0,1,2,23,132,133','doctor:gero:{gid}:elder:health:add','/gero/{id}/elder/{id}/heart_rate:POST','','','');
INSERT INTO HCDB_2.dbo.T_PRIVILEGE (name,parent_id,parent_ids,permission,api,href,icon,notes) VALUES (
'修改养老院老人密码',82,'0,1,2,23,32,82','admin:gero:{gid}:staff:info:update','/user/{id}/password:PUT','','','');
INSERT INTO HCDB_2.dbo.T_PRIVILEGE (name,parent_id,parent_ids,permission,api,href,icon,notes) VALUES (
'修改养老院职工密码',76,'0,1,2,23,32,76','admin:gero:{gid}:elder:info:update','/user/{id}/password:PUT','','','');
INSERT INTO HCDB_2.dbo.T_PRIVILEGE (name,parent_id,parent_ids,permission,api,href,icon,notes) VALUES (
'修改员工角色',67,'0,1,2,23,32,67','admin:gero:{gid}:user:role:update','/user/{id}/role:PUT','','','');
INSERT INTO HCDB_2.dbo.T_PRIVILEGE (name,parent_id,parent_ids,permission,api,href,icon,notes) VALUES (
'超级管理员',2,'0,1,2','','','no','','');
INSERT INTO HCDB_2.dbo.T_PRIVILEGE (name,parent_id,parent_ids,permission,api,href,icon,notes) VALUES (
'查询用户列表',143,'0,1,2,143','super:user:list:read','/user:GET','','','');
INSERT INTO HCDB_2.dbo.T_PRIVILEGE (name,parent_id,parent_ids,permission,api,href,icon,notes) VALUES (
'修改上班时间安排',103,'0,1,2,23,32,102,103','admin:gero:{gid}:schedule:add','/gero/{id}/staff/{id}/schedule:POST','','','');
INSERT INTO HCDB_2.dbo.T_PRIVILEGE (name,parent_id,parent_ids,permission,api,href,icon,notes) VALUES (
'家属信息管理',32,'0,1,2,23,32','','',NULL,NULL,NULL);
INSERT INTO HCDB_2.dbo.T_PRIVILEGE (name,parent_id,parent_ids,permission,api,href,icon,notes) VALUES (
'家属信息管理列表',146,'0,1,2,23,32,146','admin:gero:{gid}:relative:info:read','/gero/{id}/relative:GET','/relative',NULL,NULL);
INSERT INTO HCDB_2.dbo.T_PRIVILEGE (name,parent_id,parent_ids,permission,api,href,icon,notes) VALUES (
'删除家属信息',146,'0,1,2,23,32,146','admin:gero:{gid}:relative:info:delete','/gero/{gid}/relative/{id}:DELETE',NULL,NULL,NULL);
INSERT INTO HCDB_2.dbo.T_PRIVILEGE (name,parent_id,parent_ids,permission,api,href,icon,notes) VALUES (
'查找家属信息',146,'0,1,2,23,32,146','admin:gero:{gid}:relative:info:read','/gero/{gid}/relative/{id}:GET',NULL,NULL,NULL);
INSERT INTO HCDB_2.dbo.T_PRIVILEGE (name,parent_id,parent_ids,permission,api,href,icon,notes) VALUES (
'修改家属信息',146,'0,1,2,23,32,146','admin:gero:{gid}:relative:info:update','/gero/{gid}/relative/{id}:PUT',NULL,NULL,NULL);
INSERT INTO HCDB_2.dbo.T_PRIVILEGE (name,parent_id,parent_ids,permission,api,href,icon,notes) VALUES (
'新增家属信息',146,'0,1,2,23,32,146','admin:gero:{gid}:relative:info:add','/gero/{gid}/relative:POST',NULL,NULL,NULL);

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
           ,'整理厕所'
           ,'toilet_clean'
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
           ,'冲开水'
           ,'water_get'
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
           ,'清扫阳台'
           ,'balcony_clean'
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
           ,'更换床单'
           ,'bedsheet_change'
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
           ,'整理房间'
           ,'room_clean'
           ,1
           ,1
           ,'')
GO

---------------------------------------------------------------------------------------

-- 生成默认养老院老人项目

INSERT INTO HCDB_2.dbo.T_CARE_ITEM (gero_id,name,icon,level,period,frequency,start_time,end_time,notes,del_flag) VALUES (
1,'漱口','mouth_wash',1,1,2,'05:00:00','09:00:00','','0');
INSERT INTO HCDB_2.dbo.T_CARE_ITEM (gero_id,name,icon,level,period,frequency,start_time,end_time,notes,del_flag) VALUES (
1,'刷牙','teeth_brush',1,1,2,'05:00:00','09:00:00','','0');
INSERT INTO HCDB_2.dbo.T_CARE_ITEM (gero_id,name,icon,level,period,frequency,start_time,end_time,notes,del_flag) VALUES (
1,'梳头','hair_comb',1,1,1,'05:00:00','09:00:00','','0');
INSERT INTO HCDB_2.dbo.T_CARE_ITEM (gero_id,name,icon,level,period,frequency,start_time,end_time,notes,del_flag) VALUES (
1,'更换衣服','clothes_change',1,1,1,NULL,NULL,'','0');
INSERT INTO HCDB_2.dbo.T_CARE_ITEM (gero_id,name,icon,level,period,frequency,start_time,end_time,notes,del_flag) VALUES (
1,'发放药物','medicine_distribute',1,1,1,NULL,NULL,'','0');
INSERT INTO HCDB_2.dbo.T_CARE_ITEM (gero_id,name,icon,level,period,frequency,start_time,end_time,notes,del_flag) VALUES (
1,'剃须','beard_shave',1,3,1,NULL,NULL,'','0');
INSERT INTO HCDB_2.dbo.T_CARE_ITEM (gero_id,name,icon,level,period,frequency,start_time,end_time,notes,del_flag) VALUES (
1,'剪指甲','nail_cut',1,7,1,NULL,NULL,'','0');
INSERT INTO HCDB_2.dbo.T_CARE_ITEM (gero_id,name,icon,level,period,frequency,start_time,end_time,notes,del_flag) VALUES (
1,'饮水','water_drink',1,1,1,NULL,NULL,'','0');
INSERT INTO HCDB_2.dbo.T_CARE_ITEM (gero_id,name,icon,level,period,frequency,start_time,end_time,notes,del_flag) VALUES (
1,'穿（脱）衣','clothes_on_off',1,1,1,NULL,NULL,'','0');
INSERT INTO HCDB_2.dbo.T_CARE_ITEM (gero_id,name,icon,level,period,frequency,start_time,end_time,notes,del_flag) VALUES (
1,'协助如厕','toilet_assist',1,1,1,NULL,NULL,'','0');
INSERT INTO HCDB_2.dbo.T_CARE_ITEM (gero_id,name,icon,level,period,frequency,start_time,end_time,notes,del_flag) VALUES (
1,'喂食','food_feed',1,1,1,NULL,NULL,'','0');
INSERT INTO HCDB_2.dbo.T_CARE_ITEM (gero_id,name,icon,level,period,frequency,start_time,end_time,notes,del_flag) VALUES (
1,'更换尿布','diaper_change',1,1,1,NULL,NULL,'','0');
INSERT INTO HCDB_2.dbo.T_CARE_ITEM (gero_id,name,icon,level,period,frequency,start_time,end_time,notes,del_flag) VALUES (
1,'清洗衣物','clothes_wash',1,1,1,NULL,NULL,'','0');
INSERT INTO HCDB_2.dbo.T_CARE_ITEM (gero_id,name,icon,level,period,frequency,start_time,end_time,notes,del_flag) VALUES (
1,'清洗尿布','diaper_wash',1,1,1,NULL,NULL,'','0');
INSERT INTO HCDB_2.dbo.T_CARE_ITEM (gero_id,name,icon,level,period,frequency,start_time,end_time,notes,del_flag) VALUES (
1,'食物粉碎','food_comminute',1,1,1,NULL,NULL,'','0');
INSERT INTO HCDB_2.dbo.T_CARE_ITEM (gero_id,name,icon,level,period,frequency,start_time,end_time,notes,del_flag) VALUES (
1,'心理护理','mental_nursing',1,1,1,NULL,NULL,'','0');
INSERT INTO HCDB_2.dbo.T_CARE_ITEM (gero_id,name,icon,level,period,frequency,start_time,end_time,notes,del_flag) VALUES (
1,'洗澡','shower_take',1,3,1,NULL,NULL,'','0');
INSERT INTO HCDB_2.dbo.T_CARE_ITEM (gero_id,name,icon,level,period,frequency,start_time,end_time,notes,del_flag) VALUES (
1,'洗脸','face_wash',1,1,1,'05:00:00','09:00:00','','0');
INSERT INTO HCDB_2.dbo.T_CARE_ITEM (gero_id,name,icon,level,period,frequency,start_time,end_time,notes,del_flag) VALUES (
1,'洗脚','feet_wash',1,1,1,'20:00:00','23:59:59','','0');
INSERT INTO HCDB_2.dbo.T_CARE_ITEM (gero_id,name,icon,level,period,frequency,start_time,end_time,notes,del_flag) VALUES (
1,'洗手','hands_wash',1,1,1,NULL,NULL,'','0');
INSERT INTO HCDB_2.dbo.T_CARE_ITEM (gero_id,name,icon,level,period,frequency,start_time,end_time,notes,del_flag) VALUES (
1,'漱口','mouth_wash',1,1,2,'20:00:00','23:59:59',NULL,'0');
INSERT INTO HCDB_2.dbo.T_CARE_ITEM (gero_id,name,icon,level,period,frequency,start_time,end_time,notes,del_flag) VALUES (
1,'刷牙','teeth_brush',1,1,2,'20:00:00','23:59:59',NULL,'0');
INSERT INTO HCDB_2.dbo.T_CARE_ITEM (gero_id,name,icon,level,period,frequency,start_time,end_time,notes,del_flag) VALUES (
1,'洗脸','face_wash',1,1,2,'20:00:00','23:59:59',NULL,'0');

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

-- 给默认角色赋予默认权限

-- 超管

INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
1,1);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
1,2);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
1,15);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
1,3);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
1,23);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
1,143);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
1,144);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
1,16);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
1,20);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
1,21);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
1,22);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
1,17);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
1,18);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
1,19);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
1,116);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
1,24);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
1,29);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
1,132);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
1,32);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
1,117);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
1,124);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
1,129);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
1,118);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
1,119);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
1,120);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
1,121);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
1,122);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
1,123);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
1,125);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
1,126);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
1,127);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
1,128);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
1,130);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
1,131);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
1,133);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
1,134);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
1,135);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
1,136);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
1,137);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
1,138);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
1,139);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
1,25);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
1,26);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
1,27);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
1,28);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
1,30);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
1,31);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
1,88);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
1,95);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
1,33);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
1,36);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
1,42);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
1,61);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
1,67);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
1,76);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
1,102);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
1,82);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
1,111);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
1,103);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
1,106);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
1,145);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
1,104);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
1,105);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
1,110);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
1,107);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
1,108);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
1,109);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
1,112);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
1,113);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
1,114);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
1,115);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
1,34);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
1,35);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
1,37);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
1,38);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
1,39);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
1,40);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
1,41);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
1,43);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
1,49);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
1,55);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
1,44);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
1,45);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
1,46);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
1,47);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
1,48);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
1,50);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
1,51);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
1,52);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
1,53);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
1,54);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
1,56);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
1,57);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
1,58);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
1,59);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
1,60);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
1,62);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
1,63);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
1,64);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
1,65);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
1,66);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
1,142);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
1,68);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
1,69);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
1,70);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
1,71);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
1,72);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
1,73);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
1,74);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
1,75);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
1,141);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
1,77);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
1,78);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
1,79);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
1,80);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
1,81);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
1,140);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
1,83);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
1,84);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
1,85);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
1,86);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
1,87);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
1,89);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
1,90);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
1,91);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
1,92);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
1,93);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
1,94);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
1,96);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
1,99);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
1,97);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
1,98);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
1,100);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
1,101);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
1,14);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
1,4);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
1,9);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
1,5);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
1,6);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
1,7);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
1,8);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
1,10);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
1,11);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
1,12);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
1,13);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
1,146);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
1,147);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
1,148);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
1,149);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
1,150);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
1,151);

-- 管理员
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
2,1);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
2,2);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
2,15);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
2,3);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
2,23);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
2,16);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
2,20);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
2,21);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
2,22);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
2,17);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
2,18);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
2,19);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
2,116);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
2,24);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
2,29);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
2,132);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
2,32);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
2,117);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
2,124);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
2,129);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
2,118);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
2,119);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
2,120);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
2,121);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
2,122);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
2,123);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
2,125);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
2,126);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
2,127);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
2,128);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
2,130);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
2,131);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
2,133);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
2,134);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
2,135);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
2,136);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
2,137);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
2,138);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
2,139);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
2,25);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
2,26);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
2,27);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
2,28);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
2,30);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
2,31);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
2,88);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
2,95);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
2,33);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
2,36);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
2,42);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
2,61);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
2,67);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
2,76);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
2,102);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
2,82);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
2,111);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
2,103);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
2,106);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
2,145);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
2,104);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
2,105);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
2,110);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
2,107);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
2,108);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
2,109);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
2,112);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
2,113);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
2,114);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
2,115);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
2,34);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
2,35);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
2,37);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
2,38);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
2,39);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
2,40);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
2,41);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
2,43);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
2,49);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
2,55);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
2,44);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
2,45);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
2,46);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
2,47);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
2,48);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
2,50);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
2,51);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
2,52);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
2,53);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
2,54);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
2,56);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
2,57);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
2,58);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
2,59);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
2,60);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
2,62);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
2,63);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
2,64);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
2,65);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
2,66);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
2,142);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
2,68);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
2,69);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
2,70);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
2,71);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
2,72);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
2,73);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
2,74);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
2,75);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
2,141);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
2,77);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
2,78);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
2,79);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
2,80);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
2,81);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
2,140);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
2,83);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
2,84);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
2,85);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
2,86);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
2,87);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
2,89);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
2,90);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
2,91);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
2,92);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
2,93);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
2,94);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
2,96);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
2,99);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
2,97);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
2,98);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
2,100);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
2,101);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
2,14);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
2,4);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
2,9);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
2,5);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
2,6);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
2,7);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
2,8);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
2,10);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
2,11);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
2,12);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
2,13);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
2,146);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
2,147);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
2,148);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
2,149);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
2,150);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
2,151);

-- 老人护工

INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
3,1);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
3,2);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
3,23);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
3,3);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
3,24);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
3,29);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
3,116);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
3,117);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
3,124);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
3,129);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
3,118);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
3,119);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
3,120);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
3,121);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
3,122);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
3,123);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
3,125);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
3,126);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
3,127);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
3,128);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
3,130);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
3,131);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
3,25);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
3,26);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
3,27);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
3,28);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
3,30);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
3,31);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
3,14);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
3,4);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
3,9);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
3,5);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
3,6);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
3,7);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
3,8);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
3,10);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
3,11);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
3,12);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
3,13);

-- 房间护工

INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
4,1);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
4,2);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
4,23);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
4,3);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
4,24);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
4,29);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
4,116);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
4,117);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
4,124);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
4,129);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
4,118);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
4,119);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
4,120);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
4,121);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
4,122);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
4,123);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
4,125);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
4,126);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
4,127);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
4,128);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
4,130);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
4,131);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
4,25);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
4,26);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
4,27);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
4,28);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
4,30);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
4,31);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
4,14);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
4,4);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
4,9);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
4,5);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
4,6);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
4,7);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
4,8);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
4,10);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
4,11);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
4,12);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
4,13);

-- 医生

INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
5,1);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
5,2);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
5,23);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
5,3);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
5,24);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
5,29);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
5,132);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
5,133);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
5,134);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
5,135);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
5,136);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
5,137);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
5,138);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
5,139);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
5,25);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
5,26);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
5,27);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
5,28);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
5,30);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
5,31);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
5,14);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
5,4);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
5,9);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
5,5);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
5,6);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
5,7);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
5,8);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
5,10);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
5,11);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
5,12);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
5,13);

-- 老人

INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
6,1);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
6,2);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
6,15);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
6,3);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
6,22);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
6,16);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
6,20);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
6,21);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
6,17);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
6,18);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
6,19);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
6,14);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
6,4);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
6,9);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
6,5);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
6,6);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
6,7);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
6,8);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
6,11);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
6,12);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
6,13);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
6,10);

-- 家属

INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
7,1);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
7,2);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
7,15);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
7,3);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
7,22);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
7,16);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
7,20);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
7,21);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
7,17);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
7,18);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
7,19);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
7,14);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
7,4);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
7,9);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
7,5);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
7,6);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
7,7);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
7,8);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
7,11);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
7,12);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
7,13);
INSERT INTO HCDB_2.dbo.T_ROLE_PRIVILEGES (role_id,privilege_id) VALUES (
7,10);


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
     VALUES('default_elder','默认老人','02a3f0772fcca9f415adc990734b45c6f059c7d33ee28362c4852032','2','1','2015-03-05 00:00:00.000','1','1970-01-01','18888888888','1')
GO

INSERT INTO [dbo].[T_ELDER]([name],[gero_id],[area_id])
     VALUES('默认老人',1,4)
GO

-- 生成默认家属

INSERT INTO [dbo].[T_USER]([username],[name],[password],[user_type],[user_id],[register_date],[identity_no],[birthday],[phone_no],[gero_id])
     VALUES('default_relative','默认家属','02a3f0772fcca9f415adc990734b45c6f059c7d33ee28362c4852032','3','1','2015-03-05 00:00:00.000','1','1970-01-01','18888888888','1')
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
