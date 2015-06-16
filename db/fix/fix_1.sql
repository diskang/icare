ALTER TABLE T_ELDER_RELATIVE DROP CONSTRAINT fk_ELDER_RELATIVE_elder_id;
alter table T_ELDER_RELATIVE drop column elder_id;
alter table T_USER alter column gero_id int null;
alter table T_USER alter column wechat_id varchar(32);
alter table T_USER add union_id varchar(32);
alter table T_USER add subscribe char(1);
alter table T_USER add subscribe_time datetime;
CREATE TABLE T_ELDER_RELATIVE_RELATIONSHIP
(
	relative_user_id	int				NOT NULL,				--家属user_ID
	elder_user_id		int				NOT NULL,				--老人user_ID
);
