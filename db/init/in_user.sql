USE [{DATABASE_NAME}]
;
---------------------------------------------------------------------------------------

-- 生成超级管理员

INSERT INTO [dbo].[T_USER]([username],[name],[password],[user_type],[user_id],[register_date],[identity_no],[birthday],[phone_no],[gero_id])
     VALUES('su','超级管理员','02a3f0772fcca9f415adc990734b45c6f059c7d33ee28362c4852032','0','0','2015-03-05 00:00:00.000','1','1970-01-01','18888888888','1')
;

---------------------------------------------------------------------------------------

-- 生成默认养老院管理员

INSERT INTO [dbo].[T_USER]([username],[name],[password],[user_type],[user_id],[register_date],[identity_no],[birthday],[phone_no],[gero_id])
     VALUES('default_admin','默认管理员','02a3f0772fcca9f415adc990734b45c6f059c7d33ee28362c4852032','1','1','2015-03-05 00:00:00.000','1','1970-01-01','18888888888','1')
;

INSERT INTO [dbo].[T_STAFF]([name],[gero_id])
     VALUES('默认管理员',1)
;

---------------------------------------------------------------------------------------

-- 生成默认养老院职工

-- 生成默认老人护工

INSERT INTO [dbo].[T_USER]([username],[name],[password],[user_type],[user_id],[register_date],[identity_no],[birthday],[phone_no],[gero_id])
     VALUES('default_elder_carer','默认老人护工','02a3f0772fcca9f415adc990734b45c6f059c7d33ee28362c4852032','1','2','2015-03-05 00:00:00.000','1','1970-01-01','18888888888','1')
;

INSERT INTO [dbo].[T_STAFF]([name],[gero_id])
     VALUES('默认老人护工',1)
;

-- 生成默认房间护工

INSERT INTO [dbo].[T_USER]([username],[name],[password],[user_type],[user_id],[register_date],[identity_no],[birthday],[phone_no],[gero_id])
     VALUES('default_room_carer','默认房间护工','02a3f0772fcca9f415adc990734b45c6f059c7d33ee28362c4852032','1','3','2015-03-05 00:00:00.000','1','1970-01-01','18888888888','1')
;

INSERT INTO [dbo].[T_STAFF]([name],[gero_id])
     VALUES('默认房间护工',1)
;

-- 生成默认医生

INSERT INTO [dbo].[T_USER]([username],[name],[password],[user_type],[user_id],[register_date],[identity_no],[birthday],[phone_no],[gero_id])
     VALUES('default_doctor','默认医生','02a3f0772fcca9f415adc990734b45c6f059c7d33ee28362c4852032','1','4','2015-03-05 00:00:00.000','1','1970-01-01','18888888888','1')
;

INSERT INTO [dbo].[T_STAFF]([name],[gero_id])
     VALUES('默认医生',1)
;

---------------------------------------------------------------------------------------

-- 生成养老院默认老人、家属

-- 生成默认老人

INSERT INTO [dbo].[T_USER]([username],[name],[password],[user_type],[user_id],[register_date],[identity_no],[birthday],[phone_no],[gero_id])
     VALUES('default_elder','默认老人','02a3f0772fcca9f415adc990734b45c6f059c7d33ee28362c4852032','2','1','2015-03-05 00:00:00.000','1','1970-01-01','18888888888','1')
;

INSERT INTO [dbo].[T_ELDER]([name],[gero_id],[area_id])
     VALUES('默认老人',1,4)
;

-- 生成默认家属

INSERT INTO [dbo].[T_USER]([username],[name],[password],[user_type],[user_id],[register_date],[identity_no],[birthday],[phone_no],[gero_id])
     VALUES('default_relative','默认家属','02a3f0772fcca9f415adc990734b45c6f059c7d33ee28362c4852032','3','1','2015-03-05 00:00:00.000','1','1970-01-01','18888888888','1')
;

INSERT INTO [dbo].[T_ELDER_RELATIVE]([name])
     VALUES('默认家属')
;

---------------------------------------------------------------------------------------

-- 为用户赋予角色

INSERT INTO [dbo].[T_USER_ROLES]
           ([user_id]
           ,[role_id])
     VALUES
           ('1'
           ,'1'
		   )
;

INSERT INTO [dbo].[T_USER_ROLES]
           ([user_id]
           ,[role_id])
     VALUES
           ('2'
           ,'2'
		   )
;

INSERT INTO [dbo].[T_USER_ROLES]
           ([user_id]
           ,[role_id])
     VALUES
           ('3'
           ,'3'
		   )
;

INSERT INTO [dbo].[T_USER_ROLES]
           ([user_id]
           ,[role_id])
     VALUES
           ('4'
           ,'4'
		   )
;

INSERT INTO [dbo].[T_USER_ROLES]
           ([user_id]
           ,[role_id])
     VALUES
           ('5'
           ,'5'
		   )
;

INSERT INTO [dbo].[T_USER_ROLES]
           ([user_id]
           ,[role_id])
     VALUES
           ('6'
           ,'6'
		   )
;

INSERT INTO [dbo].[T_USER_ROLES]
           ([user_id]
           ,[role_id])
     VALUES
           ('7'
           ,'7'
		   )
;