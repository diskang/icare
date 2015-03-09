USE [HCDB_UPDATE_TEST]
GO

INSERT INTO [dbo].[T_USER]
           ([username]
           ,[name]
           ,[password]
           ,[user_type]
           ,[user_id]
           ,[register_date]
		   ,[identity_no]
		   ,[birthday]
		   ,[phone_no]
		   )
     VALUES
           ('su'
           ,'超级管理员'
           ,'02a3f0772fcca9f415adc990734b45c6f059c7d33ee28362c4852032'
           ,'0'
           ,'0'
           ,'2015-03-05 00:00:00.000'
		   ,'1'
		   ,'1970-01-01'
		   ,'18888888888'
		   )
GO

INSERT INTO [dbo].[T_USER]
           ([username]
           ,[name]
           ,[password]
           ,[user_type]
           ,[user_id]
           ,[register_date]
		   ,[identity_no]
		   ,[birthday]
		   ,[phone_no]
		   )
     VALUES
           ('admin'
           ,'管理员'
           ,'02a3f0772fcca9f415adc990734b45c6f059c7d33ee28362c4852032'
           ,'1'
           ,'1'
           ,'2015-03-05 00:00:00.000'
		   ,'1'
		   ,'1970-01-01'
		   ,'18888888888'
		   )
GO

INSERT INTO [dbo].[T_USER]
           ([username]
           ,[name]
           ,[password]
           ,[user_type]
           ,[user_id]
           ,[register_date]
		   ,[identity_no]
		   ,[birthday]
		   ,[phone_no]
		   )
     VALUES
           ('carer'
           ,'护工'
           ,'02a3f0772fcca9f415adc990734b45c6f059c7d33ee28362c4852032'
           ,'2'
           ,'2'
           ,'2015-03-05 00:00:00.000'
		   ,'1'
		   ,'1970-01-01'
		   ,'18888888888'
		   )
GO

INSERT INTO [dbo].[T_USER]
           ([username]
           ,[name]
           ,[password]
           ,[user_type]
           ,[user_id]
           ,[register_date]
		   ,[identity_no]
		   ,[birthday]
		   ,[phone_no]
		   )
     VALUES
           ('doctor'
           ,'医生'
           ,'02a3f0772fcca9f415adc990734b45c6f059c7d33ee28362c4852032'
           ,'2'
           ,'3'
           ,'2015-03-05 00:00:00.000'
		   ,'1'
		   ,'1970-01-01'
		   ,'18888888888'
		   )
GO

INSERT INTO [dbo].[T_USER]
           ([username]
           ,[name]
           ,[password]
           ,[user_type]
           ,[user_id]
           ,[register_date]
		   ,[identity_no]
		   ,[birthday]
		   ,[phone_no]
		   )
     VALUES
           ('elder'
           ,'老人'
           ,'02a3f0772fcca9f415adc990734b45c6f059c7d33ee28362c4852032'
           ,'3'
           ,'1'
           ,'2015-03-05 00:00:00.000'
		   ,'1'
		   ,'1970-01-01'
		   ,'18888888888'
		   )
GO

INSERT INTO [dbo].[T_USER]
           ([username]
           ,[name]
           ,[password]
           ,[user_type]
           ,[user_id]
           ,[register_date]
		   ,[identity_no]
		   ,[birthday]
		   ,[phone_no]
		   )
     VALUES
           ('relative'
           ,'家属'
           ,'02a3f0772fcca9f415adc990734b45c6f059c7d33ee28362c4852032'
           ,'4'
           ,'1'
           ,'2015-03-05 00:00:00.000'
		   ,'1'
		   ,'1970-01-01'
		   ,'18888888888'
		   )
GO

INSERT INTO [dbo].[T_PRIVILEGE]
           ([name]
           ,[parent_id]
           ,[parent_ids]
           ,[permission]
           ,[href])
     VALUES
           ('su'
           ,0
           ,'0'
           ,'su'
           ,'/')
GO

INSERT INTO [dbo].[T_PRIVILEGE]
           ([name]
           ,[parent_id]
           ,[parent_ids]
           ,[permission]
           ,[href])
     VALUES
           ('admin'
           ,1
           ,'0,1'
           ,'admin'
           ,'/admin')
GO

INSERT INTO [dbo].[T_PRIVILEGE]
           ([name]
           ,[parent_id]
           ,[parent_ids]
           ,[permission]
           ,[href])
     VALUES
           ('staff'
           ,2
           ,'0,1,2'
           ,'staff'
           ,'/staff')
GO

INSERT INTO [dbo].[T_GERO]
           ([name]
           ,[city]
           ,[district])
     VALUES
           ('test'
           ,'shanghai'
           ,'minhang')
GO

INSERT INTO [dbo].[T_ROLE]
           ([name]
           ,[notes])
     VALUES
           ('su'
           ,'super user')
GO

INSERT INTO [dbo].[T_ROLE]
           ([name]
           ,[notes])
     VALUES
           ('admin'
           ,'admin')
GO

INSERT INTO [dbo].[T_ROLE]
           ([name]
           ,[notes])
     VALUES
           ('carer'
           ,'carer')
GO

INSERT INTO [dbo].[T_ROLE]
           ([name]
           ,[notes])
     VALUES
           ('doctor'
           ,'doctor')
GO

INSERT INTO [dbo].[T_ROLE]
           ([name]
           ,[notes])
     VALUES
           ('elder'
           ,'elder')
GO

INSERT INTO [dbo].[T_ROLE]
           ([name]
           ,[notes])
     VALUES
           ('relative'
           ,'relative')
GO

INSERT INTO [dbo].[T_ROLE_PRIVILEGES]
           ([role_id]
           ,[privilege_id])
     VALUES
           (1
           ,1)
GO

INSERT INTO [dbo].[T_ROLE_PRIVILEGES]
           ([role_id]
           ,[privilege_id])
     VALUES
           (1
           ,2)
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