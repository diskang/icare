USE [HCDB_UPDATE_TEST]
GO

-- 建立默认养老院

INSERT INTO [dbo].[T_GERO]([name],[city],[district],[register_date])
     VALUES('default','default','default','2015-03-12')
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
           ,[notes]
           ,[del_flag])
     VALUES
           (<gero_id, int,>
           ,<name, nvarchar(32),>
           ,<icon, varchar(32),>
           ,<period, int,>
           ,<frequency, int,>
           ,<notes, nvarchar(32),>
           ,<del_flag, char(1),>)
GO




---------------------------------------------------------------------------------------


-- 生成超级管理员

INSERT INTO [dbo].[T_USER]([username],[name],[password],[user_type],[user_id],[register_date],[identity_no],[birthday],[phone_no],[gero_id])
     VALUES('su','超级管理员','02a3f0772fcca9f415adc990734b45c6f059c7d33ee28362c4852032','0','0','2015-03-05 00:00:00.000','1','1970-01-01','18888888888','1')
GO

---------------------------------------------------------------------------------------

-- 生成默认养老院管理员

INSERT INTO [dbo].[T_USER]([username],[name],[password],[user_type],[user_id],[register_date],[identity_no],[birthday],[phone_no],[gero_id])
     VALUES('demo_admin','默认管理员','02a3f0772fcca9f415adc990734b45c6f059c7d33ee28362c4852032','1','1','2015-03-05 00:00:00.000','1','1970-01-01','18888888888','1')
GO

INSERT INTO [dbo].[T_STAFF]([name],[gero_id])
     VALUES('默认管理员',1)
GO

---------------------------------------------------------------------------------------

-- 生成默认养老院职工

-- 生成默认老人护工

INSERT INTO [dbo].[T_USER]([username],[name],[password],[user_type],[user_id],[register_date],[identity_no],[birthday],[phone_no],[gero_id])
     VALUES('elder_carer','默认老人护工','02a3f0772fcca9f415adc990734b45c6f059c7d33ee28362c4852032','1','2','2015-03-05 00:00:00.000','1','1970-01-01','18888888888','1')
GO

INSERT INTO [dbo].[T_STAFF]([name],[gero_id])
     VALUES('默认老人护工',1)
GO

-- 生成默认房间护工

INSERT INTO [dbo].[T_USER]([username],[name],[password],[user_type],[user_id],[register_date],[identity_no],[birthday],[phone_no],[gero_id])
     VALUES('room_carer','默认房间护工','02a3f0772fcca9f415adc990734b45c6f059c7d33ee28362c4852032','1','3','2015-03-05 00:00:00.000','1','1970-01-01','18888888888','1')
GO

INSERT INTO [dbo].[T_STAFF]([name],[gero_id])
     VALUES('默认房间护工',1)
GO

-- 生成默认医生

INSERT INTO [dbo].[T_USER]([username],[name],[password],[user_type],[user_id],[register_date],[identity_no],[birthday],[phone_no],[gero_id])
     VALUES('room_carer','默认医生','02a3f0772fcca9f415adc990734b45c6f059c7d33ee28362c4852032','1','4','2015-03-05 00:00:00.000','1','1970-01-01','18888888888','1')
GO

INSERT INTO [dbo].[T_STAFF]([name],[gero_id])
     VALUES('默认医生',1)
GO

-- 生成默认老人

INSERT INTO [dbo].[T_USER]([username],[name],[password],[user_type],[user_id],[register_date],[identity_no],[birthday],[phone_no],[gero_id])
     VALUES('room_carer','默认老人','02a3f0772fcca9f415adc990734b45c6f059c7d33ee28362c4852032','2','1','2015-03-05 00:00:00.000','1','1970-01-01','18888888888','1')
GO

INSERT INTO [dbo].[T_STAFF]([name],[gero_id])
     VALUES('默认老人',1)
GO




INSERT INTO [dbo].[T_ROLE]
           ([name]
           ,[notes]
		   ,[gero_id])
     VALUES
           ('su'
           ,'super user'
		   ,'1')
GO

INSERT INTO [dbo].[T_ROLE]
           ([name]
           ,[notes]
		   ,[gero_id])
     VALUES
           ('admin'
           ,'admin'
		   ,'1')
GO

INSERT INTO [dbo].[T_ROLE]
           ([name]
           ,[notes]
		   ,[gero_id])
     VALUES
           ('carer'
           ,'carer'
		   ,'1')
GO

INSERT INTO [dbo].[T_ROLE]
           ([name]
           ,[notes]
		   ,[gero_id])
     VALUES
           ('doctor'
           ,'doctor'
		   ,'1')
GO

INSERT INTO [dbo].[T_ROLE]
           ([name]
           ,[notes]
		   ,[gero_id])
     VALUES
           ('elder'
           ,'elder'
		   ,'1')
GO

INSERT INTO [dbo].[T_ROLE]
           ([name]
           ,[notes]
		   ,[gero_id])
     VALUES
           ('relative'
           ,'relative'
		   ,'1')
GO

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
           ('1'
           ,'2'
		   )
GO

INSERT INTO [dbo].[T_USER_ROLES]
           ([user_id]
           ,[role_id])
     VALUES
           ('1'
           ,'3'
		   )
GO

INSERT INTO [dbo].[T_USER_ROLES]
           ([user_id]
           ,[role_id])
     VALUES
           ('1'
           ,'4'
		   )
GO

INSERT INTO [dbo].[T_USER_ROLES]
           ([user_id]
           ,[role_id])
     VALUES
           ('2'
           ,'1'
		   )
GO

INSERT INTO [dbo].[T_ELDER]
           ([name]
           ,[gero_id])
     VALUES
           ('elder1'
		   ,1)
GO

INSERT INTO [dbo].[T_STAFF]
           ([name]
           ,[gero_id])
     VALUES
           ('staff1'
		   ,1)
GO

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