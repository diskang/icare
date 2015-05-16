import pyodbc

DATABASE_NAME = "HCDB_2_TEST"

createFile = open('create_db.sql',encoding="utf-8")
initFile = open('initialize_data.sql',encoding="utf-8")
userFile = open('in_user.sql',encoding="utf-8")

connectString = "Password=;User ID=sa;"
conn = pyodbc.connect('DRIVER={SQL Server};SERVER=localhost;DATABASE=HCDB_2_TEST;UID=housecare;PWD=laoban2014;trusted_connection=true',autocommit=True)
cur = conn.cursor()
query = " ".join(createFile.readlines())
query = query.replace("{DATABASE_NAME}",DATABASE_NAME)
cur.execute(query)
conn.commit
cur.close()

cur_i = conn.cursor()
query_i = " ".join(initFile.readlines())
query_i = query_i.replace("{DATABASE_NAME}",DATABASE_NAME)
# query_i = "INSERT INTO [dbo].[T_USER]([username],[name],[password],[user_type],[user_id],[register_date],[identity_no],[birthday],[phone_no],[gero_id])VALUES('default_elder_carer','默认老人护工','02a3f0772fcca9f415adc990734b45c6f059c7d33ee28362c4852032','1','2','2015-03-05 00:00:00.000','1','1970-01-01','18888888888','1')"
cur_i.execute(query_i)
conn.commit
cur_i.close()

cur_u = conn.cursor()
query_u = " ".join(userFile.readlines())
query_u = query_u.replace("{DATABASE_NAME}",DATABASE_NAME)
# query_u = "INSERT INTO [dbo].[T_USER]([username],[name],[password],[user_type],[user_id],[register_date],[identity_no],[birthday],[phone_no],[gero_id])VALUES('default_elder_carer','默认老人护工','02a3f0772fcca9f415adc990734b45c6f059c7d33ee28362c4852032','1','2','2015-03-05 00:00:00.000','1','1970-01-01','18888888888','1')"
cur_u.execute(query_u)
conn.commit
cur_u.close()

conn.close()