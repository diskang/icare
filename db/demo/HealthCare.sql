CREATE DATABASE HCDB_UPDATE_TEST
GO

USE HCDB_UPDATE_TEST
GO

CREATE TABLE T_USER
(
	id					int				PRIMARY KEY IDENTITY,	--用户id
	username			nvarchar(30)	NOT NULL,				--用户登录名
	name				nvarchar(20)	NOT NULL,				--用户姓名
	password			varchar(64)		NOT NULL,				--用户密码
	user_type			int				NOT NULL,				--超级管理员=0、管理员=1，员工=2、老人=3、家属=4
	user_id				int				NOT NULL,				---1，gero_id, staff_id, elder_id，family_id
	register_date		datetime		NOT NULL,				--注册日期
	cancel_date			datetime		,						--注销日期
	photo				varchar(256)	,						--照片url
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
	image_url			varchar(256)	,						--养老院封面图片的url地址
)
GO

CREATE TABLE T_ROOM
(
	id					int				PRIMARY KEY IDENTITY,	--房间id
	gero_id				int				NOT NULL,				--养老院id，关联GERO
	room_no				varchar(8)		NOT NULL,				--房间号
	floor_no			varchar(8)		NOT NULL,				--楼层
	building			nvarchar(6)		NOT NULL,				--楼号
	room_name			nvarchar(16)							--房间名
)
GO

CREATE TABLE T_BLUETOOTH_DEVICE
(
	id					int				PRIMARY KEY IDENTITY,	--蓝牙设备id
	elder_id			int				NOT NULL,				--老人id，关联ELDER
	room_id				int				NOT NULL,				--房间id，关联ROOM
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
	gender				bit				NOT NULL,				--0代表男性，1代表女性
	gero_id				int				NOT NULL,				--养老院id，关联GERO
	address				nvarchar(50) 	,						--居住地
	age					int				,						--老人年龄
	identity_no			char(18)		NOT NULL,				--老人身份证号码
	nssf_id				varchar(50)		,						--老人社保卡号
	archive_id			varchar(20)		,						--档案编号，不清楚用处，养老院要求加的。
	photo_url			varchar(256)	,						--老人照片地址
	room_id				int				,						--老人入住床号，关联T_ROOM表
	nationality			nvarchar(20)	,						--民族
	marriage			int				,						--0:未婚  1：已婚 2：离异 3:丧偶
	native_place		nvarchar(20)	,						--老人籍贯
	birthday			date			NOT NULL,				--老人出身年月日
	political_status	nvarchar(10)	,						--老人政治面貌
	education			nvarchar(50)	,						--老人受教育水平
	residence			nvarchar(64)	,						--户口所在地
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

CREATE TABLE T_ELDER_FAMILIES
(
	id					int				PRIMARY KEY IDENTITY,	--家属ID
	elder_id			int				NOT NULL,				--关联T_ELDER表
	name				nvarchar(30)	NOT NULL,				--名字
	urgent				bit				,						--是否紧急联系人
	relationship		nvarchar(20)	,						--与老人关系，optional
	phone				char(20)		NOT NULL,				--联系方式optional
	zip_code			char(10)		,						--邮编
	address				nvarchar(128)	,						--家属住址optional
	identity_no			char(18)		NOT NULL,				--身份证号 optional
	wechat_id			nvarchar(64)	,						--家属微信账号optional
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
	phone				varchar(20)		,						--
	email				varchar(20)		,						--
	idno				varchar(18)		,						--
	nssf				varchar(20)		,						--
	gero_id				int				NOT NULL,				--
	birthday			date			,						--
	gender				nvarchar(2)		,						--
	basic_url			varchar(50)		,						--
	residence_address	nvarchar(50)	,						--
	household_address	nvarchar(50)	,						--
	leave_date			date			,						--
	archive_id			varchar(20)		,						--
	photo_url			varchar(256)	,						--
)
GO

CREATE TABLE T_CAREWORK_RECORD
(
	id					int				PRIMARY KEY IDENTITY,
	carer_id			int				NOT NULL,				--
	elder_item_id		int				NOT NULL,				--
	finish_time			datetime		NOT NULL,				--
)
GO

CREATE TABLE T_ROOMWORK_RECORD
(
	id					int				PRIMARY KEY IDENTITY,
	carer_id			int				NOT NULL,				--
	room_item_id		int				NOT NULL,				--
	room_id				int				NOT NULL,				--
	finish_time			datetime		NOT NULL,				--
)
GO

CREATE TABLE T_CAREWORK_ELDER_RECORD
(
	id					int				PRIMARY KEY IDENTITY,
	carer_id			int				NOT NULL,				--
	elder_id			int				NOT NULL,				--
	elder_item_list		nvarchar(256)	NOT NULL,				--
	finish_time			datetime		NOT NULL,				--
)
GO

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

CREATE TABLE T_CARER_SCHEDULE_PLAN
(
	id					int				PRIMARY KEY IDENTITY,
	carer_id			int				NOT NULL,				--
	gero_id				int				NOT NULL,				--
	work_date			date			NOT NULL,				--
)
GO

CREATE TABLE T_CAREWORK_SCHEDULE_DETAIL
(
	id					int				PRIMARY KEY IDENTITY,
	carer_id			int				NOT NULL,				--
	elder_id			int				NOT NULL,				--
	work_date			date			NOT NULL,				--
)
GO

CREATE TABLE T_ROOMWORK_SCHEDULE_DETAIL
(
	id					int				PRIMARY KEY IDENTITY,
	carer_id			int				NOT NULL,				--
	room_id				int				NOT NULL,				--
	work_date			date			NOT NULL,				--
)
GO

CREATE TABLE T_ROLE
(
	id					int				PRIMARY KEY IDENTITY,
	gero_id				int				,						--关联T_GERO，如果为空，表明是系统角色，不能删除
	name				nvarchar(50)	NOT NULL,				--角色名称
	notes				nvarchar(32)	NOT NULL,				--备注
)
GO

CREATE TABLE T_PRIVILEGE
(
	id					int				PRIMARY KEY IDENTITY,
	name				nvarchar(50)	NOT NULL,				--
	parent_id			varchar(64)		NOT NULL,				--
	parent_ids			varchar(1000)	NOT NULL,				--
	href				varchar(255)	,						--
	icon				varchar(100)	,						--
	notes				nvarchar(500)	,						--
)
GO

CREATE TABLE T_ROLE_PRIVILEGES
(
	id					int				PRIMARY KEY IDENTITY,
	role_id				int				NOT NULL,				--
	privilege_id		int				NOT NULL,				--
)
GO

CREATE TABLE T_USER_ROLES
(
	id					int				PRIMARY KEY IDENTITY,
	user_id				int				NOT NULL,				--
	role_id				int				NOT NULL,				--
)
GO

CREATE TABLE T_CARE_ITEM
(
	id					int				PRIMARY KEY IDENTITY,
	name				nvarchar(32)	NOT NULL,				--
	level				int				NOT NULL,				--
	notes				nvarchar(32)	,						--
)
GO

CREATE TABLE T_ROOM_ITEM
(
	id					int				PRIMARY KEY IDENTITY,
	name				nvarchar(32)	NOT NULL,				--
	notes				nvarchar(32)	,						--
)
GO

CREATE TABLE T_GERO_CARE_ITEM
(
	id					int				PRIMARY KEY IDENTITY,
	gero_id				int				NOT NULL,				--
	care_item_id		int				NOT NULL,				--
	level				int				NOT NULL,				--
	period				int				,						--
	frequency			int				,						--
)
GO

CREATE TABLE T_GERO_ROOM_ITEM
(
	id					int				PRIMARY KEY IDENTITY,
	gero_id				int				NOT NULL,				--
	room_item_id		int				NOT NULL,				--
	period				int				,						--
	frequency			int				,						--
)
GO

CREATE TABLE T_ELDER_ITEM
(
	id					int				PRIMARY KEY IDENTITY,
	elder_id			int				NOT NULL,				--
	gero_care_item_id	int				NOT NULL,				--
	level				int				NOT NULL,				--
	period				int				,						--
	start_time			time			,						--
	end_time			time			,						--
)
GO

ALTER TABLE T_GERO
ADD CONSTRAINT fk_GERO_staff_id
FOREIGN KEY (contact_id)
REFERENCES T_STAFF(id)
GO

ALTER TABLE T_ROOM
ADD CONSTRAINT fk_ROOM_gero_id
FOREIGN KEY (gero_id)
REFERENCES T_GERO(id)
GO

ALTER TABLE T_BLUETOOTH_DEVICE
ADD CONSTRAINT fk_BLUETOOTH_DEVICE_elder_id
FOREIGN KEY (elder_id)
REFERENCES T_ELDER(id)
GO

ALTER TABLE T_BLUETOOTH_DEVICE
ADD CONSTRAINT fk_BLUETOOTH_DEVICE_room_id
FOREIGN KEY (room_id)
REFERENCES T_ROOM(id)
GO

ALTER TABLE T_BLUETOOTH_DEVICE
ADD CONSTRAINT fk_BLUETOOTH_DEVICE_type_id
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
ADD CONSTRAINT fk_ELDER_room_id
FOREIGN KEY (room_id)
REFERENCES T_ROOM(id)
GO

ALTER TABLE T_ELDER_SHEET
ADD CONSTRAINT fk_ELDER_SHEET_bed_id
FOREIGN KEY (elder_id)
REFERENCES T_ELDER(id)
GO

ALTER TABLE T_ELDER_FAMILIES
ADD CONSTRAINT fk_ELDER_FAMILIES_elder_id
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

ALTER TABLE T_ROOMWORK_RECORD
ADD CONSTRAINT fk_ROOMWORK_RECORD_carer_id
FOREIGN KEY (carer_id)
REFERENCES T_STAFF(id)
GO

ALTER TABLE T_ROOMWORK_RECORD
ADD CONSTRAINT fk_ROOMWORK_RECORD_room_item_id
FOREIGN KEY (room_item_id)
REFERENCES T_GERO_ROOM_ITEM(id)
GO

ALTER TABLE T_ROOMWORK_RECORD
ADD CONSTRAINT fk_ROOMWORK_RECORD_room_id
FOREIGN KEY (room_id)
REFERENCES T_ROOM(id)
GO

ALTER TABLE T_CAREWORK_ELDER_RECORD
ADD CONSTRAINT fk_CAREWORK_ELDER_RECORD_carer_id
FOREIGN KEY (carer_id)
REFERENCES T_STAFF(id)
GO

ALTER TABLE T_CAREWORK_ELDER_RECORD
ADD CONSTRAINT fk_CAREWORK_ELDER_RECORD_elder_id
FOREIGN KEY (elder_id)
REFERENCES T_ELDER(id)
GO

ALTER TABLE T_ELDER_AUDIO_RECORD
ADD CONSTRAINT fk_ELDER_AUDIO_RECORD_elder_id
FOREIGN KEY (elder_id)
REFERENCES T_ELDER(id)
GO

ALTER TABLE T_CARER_SCHEDULE_PLAN
ADD CONSTRAINT fk_CARER_SCHEDULE_PLAN_carer_id
FOREIGN KEY (carer_id)
REFERENCES T_STAFF(id)
GO

ALTER TABLE T_CARER_SCHEDULE_PLAN
ADD CONSTRAINT fk_CARER_SCHEDULE_PLAN_gero_id
FOREIGN KEY (gero_id)
REFERENCES T_GERO(id)
GO

ALTER TABLE T_CAREWORK_SCHEDULE_DETAIL
ADD CONSTRAINT fk_CAREWORK_SCHEDULE_DETAIL_carer_id
FOREIGN KEY (carer_id)
REFERENCES T_STAFF(id)
GO

ALTER TABLE T_CAREWORK_SCHEDULE_DETAIL
ADD CONSTRAINT fk_CAREWORK_SCHEDULE_DETAIL_elder_id
FOREIGN KEY (elder_id)
REFERENCES T_ELDER(id)
GO

ALTER TABLE T_ROOMWORK_SCHEDULE_DETAIL
ADD CONSTRAINT fk_ROOMWORK_SCHEDULE_DETAIL_carer_id
FOREIGN KEY (carer_id)
REFERENCES T_STAFF(id)
GO

ALTER TABLE T_ROOMWORK_SCHEDULE_DETAIL
ADD CONSTRAINT fk_ROOMWORK_SCHEDULE_DETAIL_room_id
FOREIGN KEY (room_id)
REFERENCES T_ROOM(id)
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
GO

ALTER TABLE T_ROLE_PRIVILEGES
ADD CONSTRAINT fk_ROLE_PRIVILEGES_privilege_id
FOREIGN KEY (privilege_id)
REFERENCES T_PRIVILEGE(id)
GO

ALTER TABLE T_USER_ROLES
ADD CONSTRAINT fk_USER_ROLES_staff_id
FOREIGN KEY (user_id)
REFERENCES T_USER(id)
GO

ALTER TABLE T_USER_ROLES
ADD CONSTRAINT fk_USER_ROLES_role_id
FOREIGN KEY (role_id)
REFERENCES T_ROLE(id)
GO

ALTER TABLE T_GERO_CARE_ITEM
ADD CONSTRAINT fk_GERO_CARE_ITEM_gero_id
FOREIGN KEY (gero_id)
REFERENCES T_GERO(id)
GO

ALTER TABLE T_GERO_CARE_ITEM
ADD CONSTRAINT fk_GERO_CARE_ITEM_care_item_id
FOREIGN KEY (care_item_id)
REFERENCES T_CARE_ITEM(id)
GO

ALTER TABLE T_GERO_ROOM_ITEM
ADD CONSTRAINT fk_GERO_ROOM_ITEM_gero_id
FOREIGN KEY (gero_id)
REFERENCES T_GERO(id)
GO

ALTER TABLE T_GERO_ROOM_ITEM
ADD CONSTRAINT fk_GERO_ROOM_ITEM_room_item_id
FOREIGN KEY (room_item_id)
REFERENCES T_ROOM_ITEM(id)
GO

ALTER TABLE T_ELDER_ITEM
ADD CONSTRAINT fk_ELDER_ITEM_elder_id
FOREIGN KEY (elder_id)
REFERENCES T_ELDER(id)
GO

ALTER TABLE T_ELDER_ITEM
ADD CONSTRAINT fk_ELDER_ITEM_gero_care_item_id
FOREIGN KEY (gero_care_item_id)
REFERENCES T_GERO_CARE_ITEM(id)
GO






















