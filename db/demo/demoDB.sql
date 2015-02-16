DROP TABLE T_GERO;
DROP TABLE T_USER;

CREATE TABLE T_GERO
(
	id int NOT NULL AUTO_INCREMENT COMMENT '编号',
	name varchar(50) NOT NULL COMMENT '养老院名称',
	cancel_date datetime,
	PRIMARY KEY (id)
) COMMENT = '机构表';

CREATE TABLE T_USER
(

	id int NOT NULL AUTO_INCREMENT COMMENT '编号',
	username varchar(30) NOT NULL COMMENT '登录名',
	password varchar(80) NOT NULL COMMENT '密码',
	user_id int NOT NULL COMMENT '用户id',	
	user_type int NOT NULL COMMENT '用户类型',
	register_date Date COMMENT '注册时间',
	cancel_date	Date COMMENT '注销时间',
    PRIMARY KEY (id)
) COMMENT = '用户表';

