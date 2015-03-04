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
	id					int				PRIMARY KEY IDENTITY,
	name				nvarchar(50)	NOT NULL,
	city				nvarchar(10)	NOT NULL,
	district			nvarchar(10)	NOT NULL,
	address				nvarchar(30)	,
	contact				varchar(20)		,
	licence				varchar(30)		,
	scale				int				,
	care_level			int				,
	contact_id			int				,
	logo_url			varchar(256)	,
	image_url			varchar(256)	,
)
GO

CREATE TABLE T_ROOM
(
	id					int				PRIMARY KEY IDENTITY,
	gero_id				int				NOT NULL,
	room_no				varchar(8)		NOT NULL,
	floor_no			varchar(8)		NOT NULL,
	building			nvarchar(6)		NOT NULL,
	room_name			nvarchar(16)
)
GO

CREATE TABLE T_BLUETOOTH_DEVICE
(
	id					int				PRIMARY KEY IDENTITY,
	elder_id			int				NOT NULL,
	room_id				int				NOT NULL,
	name				nvarchar(30)	NOT NULL,
	device_type_id		int				NOT NULL,
	active_mode			varchar(10)		NOT NULL,
)
GO

CREATE TABLE T_DEVICE_TYPE
(
	id					int				PRIMARY KEY IDENTITY,
	name				nvarchar(20)	NOT NULL,
	notes				nvarchar(32)	,
)
GO

CREATE TABLE T_GERO_ELDER_EXCHANGE
(
	id					int				PRIMARY KEY IDENTITY,
	participants		nvarchar(200)	NOT NULL,
	mediators			nvarchar(200)	NOT NULL,
	description			ntext			,
	result				nvarchar(200)	NOT NULL,
	recorder			int				NOT NULL,
	time				datetime		NOT NULL,
	gero_id				int				NOT NULL,
)

CREATE TABLE T_ELDER
(
	id					int				PRIMARY KEY IDENTITY,
	name				nvarchar(20) 	NOT NULL,
	gender				bit				NOT NULL,
	gero_id				int				NOT NULL,
	address				nvarchar(50) 	,
	age					int				,
	identity_no			char(18)		NOT NULL,
	nssf_id				varchar(50)		,
	archive_id			varchar(20)		,
	photo_url			varchar(256)	,
	room_id				int				,
	nationality			nvarchar(20)	,
	marriage			int				,
	native_place		nvarchar(20)	,
	birthday			date			NOT NULL,
	political_status	nvarchar(10)	,
	education			nvarchar(50)	,
	residence			nvarchar(64)	,
	care_level			int				,
	checkin_date		date			,
	checkout_date		date			,
	apply_url			varchar(256)	,
	survey_url			varchar(256)	,
	assess_url			varchar(256)	,
	track_url			varchar(256)	,
	pad_mac				varchar(17)		,
)
GO

CREATE TABLE T_ELDER_SHEET
(
	elder_id			int				PRIMARY KEY IDENTITY,
	apply_name			nvarchar(20)	,
	apply_date			date			,
	medical_history		ntext			,
	apply_reason		nvarchar(50)	,
	surveyor_signature	nvarchar(20)	NOT NULL,
	surveyor_role		nvarchar(20)	,
	survey_date			date			,
	survey_result		bit				NOT NULL,
	survey_special_item	nvarchar(1000)	,
	test_result			nvarchar(1000)	NOT NULL,
	doctor_signature	nvarchar(20)	,
	assess_care_level	int				NOT NULL,
	assess_signature	nvarchar(20)	,
	admin_decision		bit				NOT NULL,
	register_no			int				,
	approve_signature	nvarchar(20)	
)
GO

CREATE TABLE T_ELDER_FAMILIES
(
	id					int				PRIMARY KEY IDENTITY,
	elder_id			int				NOT NULL,
	name				nvarchar(30)	NOT NULL,
	urgent				bit				,
	relationship		nvarchar(20)	,
	phone				char(20)		NOT NULL,
	zip_code			char(10)		,
	address				nvarchar(128)	,
	identity_no			char(18)		NOT NULL,
	wechat_id			nvarchar(64)	,
)
GO

CREATE TABLE T_SELFCARE_ITEM
(
	id					int				PRIMARY KEY IDENTITY,
	item_name			nvarchar(20)	NOT NULL,
	gero_id				int				NOT NULL,
)
GO

CREATE TABLE T_ELDER_SELFCARE_STATUS
(
	id					int				PRIMARY KEY IDENTITY,
	elder_id			int				NOT NULL,
	selfcare_item_id	int				NOT NULL,
	level				int				NOT NULL,
)
GO

CREATE TABLE T_ELDER_TEMPERATURE
(
	id					int				PRIMARY KEY IDENTITY,
	elder_id			int				NOT NULL,
	doctor_id			int				NOT NULL,
	temperature			float			NOT NULL,
	time				datetime		NOT NULL,
)

CREATE TABLE T_ELDER_HEART_RATE
(
	id					int				PRIMARY KEY IDENTITY,
	elder_id			int				NOT NULL,
	doctor_id			int				NOT NULL,
	rate				float			NOT NULL,
	time				datetime		NOT NULL,
)

CREATE TABLE T_ELDER_BLOOD_PRESSURE
(
	id					int				PRIMARY KEY IDENTITY,
	elder_id			int				NOT NULL,
	doctor_id			int				NOT NULL,
	diastolic_pressure	float			NOT NULL,
	systolic_pressure	float			NOT NULL,
	time				datetime		NOT NULL,
)

CREATE TABLE T_STAFF
(
	id					int				PRIMARY KEY IDENTITY,
	name				nvarchar(20)	NOT NULL,
	phone				varchar(20)		,
	email				varchar(20)		,
	idno				varchar(18)		,
	nssf				varchar(20)		,
	gero_id				int				NOT NULL,
	birthday			date			,
	gender				nvarchar(2)		,
	basic_url			varchar(50)		,
	residence_address	nvarchar(50)	,
	household_address	nvarchar(50)	,
	leave_date			date			,
	archive_id			varchar(20)		,
	photo_url			varchar(256)	,
)
GO

CREATE TABLE T_CAREWORK_RECORD
(
	id					int				PRIMARY KEY IDENTITY,
	carer_id			int				NOT NULL,
	elder_item_id		int				NOT NULL,
	finish_time			datetime		NOT NULL,
)
GO

CREATE TABLE T_ROOMWORK_RECORD
(
	id					int				PRIMARY KEY IDENTITY,
	carer_id			int				NOT NULL,
	room_item_id		int				NOT NULL,
	room_id				int				NOT NULL,
	finish_time			datetime		NOT NULL,
)
GO

CREATE TABLE T_CAREWORK_ELDER_RECORD
(
	id					int				PRIMARY KEY IDENTITY,
	carer_id			int				NOT NULL,
	elder_id			int				NOT NULL,
	elder_item_list		nvarchar(256)	NOT NULL,
	finish_time			datetime		NOT NULL,
)
GO

CREATE TABLE T_ELDER_AUDIO_RECORD
(
	id					int				PRIMARY KEY IDENTITY,
	recorder_identity	int				NOT NULL,
	recorder_id			int				NOT NULL,
	listener_identity	int				NOT NULL,
	listener_id			int				NOT NULL,
	elder_id			int				NOT NULL,
	record_time			datetime		NOT NULL,
	url					nvarchar(256)	NOT NULL,
	read_times			int				NOT NULL,
)
GO

CREATE TABLE T_CARER_SCHEDULE_PLAN
(
	id					int				PRIMARY KEY IDENTITY,
	carer_id			int				NOT NULL,
	gero_id				int				NOT NULL,
	work_date			date			NOT NULL,
)
GO

CREATE TABLE T_CAREWORK_SCHEDULE_DETAIL
(
	id					int				PRIMARY KEY IDENTITY,
	carer_id			int				NOT NULL,
	elder_id			int				NOT NULL,
	work_date			date			NOT NULL,
)
GO

CREATE TABLE T_ROOMWORK_SCHEDULE_DETAIL
(
	id					int				PRIMARY KEY IDENTITY,
	carer_id			int				NOT NULL,
	room_id				int				NOT NULL,
	work_date			date			NOT NULL,
)
GO

CREATE TABLE T_ROLE
(
	id					int				PRIMARY KEY IDENTITY,
	gero_id				int				,
	name				nvarchar(50)	NOT NULL,
	notes				nvarchar(32)	NOT NULL,
)
GO

CREATE TABLE T_PRIVILEGE
(
	id					int				PRIMARY KEY IDENTITY,
	name				nvarchar(50)	NOT NULL,
	pid					varchar(64)		NOT NULL,
	pids				varchar(1000)	NOT NULL,
	href				varchar(255)	,
	icon				varchar(100)	,
	notes				nvarchar(500)	,
)
GO

CREATE TABLE T_ROLE_PRIVILEGES
(
	id					int				PRIMARY KEY IDENTITY,
	role_id				int				NOT NULL,
	privilege_id		int				NOT NULL,
)
GO

CREATE TABLE T_STAFF_ROLES
(
	id					int				PRIMARY KEY IDENTITY,
	staff_id			int				NOT NULL,
	role_id				int				NOT NULL,
)
GO

CREATE TABLE T_CARE_ITEM
(
	id					int				PRIMARY KEY IDENTITY,
	name				nvarchar(32)	NOT NULL,
	level				int				NOT NULL,
	notes				nvarchar(32)	,
)
GO

CREATE TABLE T_ROOM_ITEM
(
	id					int				PRIMARY KEY IDENTITY,
	name				nvarchar(32)	NOT NULL,
	notes				nvarchar(32)	,
)
GO

CREATE TABLE T_GERO_CARE_ITEM
(
	id					int				PRIMARY KEY IDENTITY,
	gero_id				int				NOT NULL,
	care_item_id		int				NOT NULL,
	level				int				NOT NULL,
	period				int				,
	frequency			int				,
)
GO

CREATE TABLE T_GERO_ROOM_ITEM
(
	id					int				PRIMARY KEY IDENTITY,
	gero_id				int				NOT NULL,
	room_item_id		int				NOT NULL,
	period				int				,
	frequency			int				,
)
GO

CREATE TABLE T_ELDER_ITEM
(
	id					int				PRIMARY KEY IDENTITY,
	elder_id			int				NOT NULL,
	gero_care_item_id	int				NOT NULL,
	level				int				NOT NULL,
	period				int				,
	start_time			time			,
	end_time			time			,
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

ALTER TABLE T_STAFF_ROLES
ADD CONSTRAINT fk_STAFF_ROLES_staff_id
FOREIGN KEY (staff_id)
REFERENCES T_STAFF(id)
GO

ALTER TABLE T_STAFF_ROLES
ADD CONSTRAINT fk_STAFF_ROLES_role_id
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






















