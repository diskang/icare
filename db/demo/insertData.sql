USE [HCDB_UPDATE_TEST]
GO

INSERT INTO [dbo].[T_GERO]
           ([name]
           ,[city]
           ,[district]
		   ,[register_date])
     VALUES
           ('default'
           ,'default'
           ,'default'
		   ,'2015-03-12')
GO

INSERT INTO [dbo].[T_GERO]
           ([name]
           ,[city]
           ,[district]
		   ,[register_date])
     VALUES
           ('test'
           ,'shanghai'
           ,'minhang'
		   ,'2015-03-11')
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
		   ,[gero_id]
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
		   ,'1'
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
		   ,[gero_id]
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
		   ,'1'
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
		   ,[gero_id]
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
		   ,'1'
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
		   ,[gero_id]
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
		   ,'1'
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
		   ,[gero_id]
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
		   ,'1'
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
		   ,[gero_id]
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
		   ,'1'
		   )
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