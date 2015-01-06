drop table if exists T_DEPT;
drop table if exists T_USER;
create table T_DEPT
(
  id        bigint auto_increment,
  dept_name VARCHAR(20),
  parent_id bigint,
  state     varchar(1),
  remark    VARCHAR(100),
  primary key (id)
)engine=InnoDB;
create table T_USER
(
  id       bigint auto_increment,
  user_code VARCHAR(20),
  user_name VARCHAR(50),
  password  VARCHAR(50),
  state     VARCHAR(1),
  remark    VARCHAR(100),
  salt      VARCHAR(50),
  dept_id   bigint,
   primary key (id)
)engine=InnoDB;


insert into T_DEPT (id, dept_name, parent_id, state, remark)
values (1, '警视通', 0, '1', '1');
insert into T_DEPT (id, dept_name, parent_id, state, remark)
values (2, '开发二部', 1, '1', '1');
insert into T_DEPT (id, dept_name, parent_id, state, remark)
values (3, '开发二部', 1, '1', '1');
commit;

insert into T_USER (id, user_code, user_name, password, state, remark, salt, dept_id)
values (62, 'test1', 'test', 'c91faa552a96f705f00d82e2164948fd8912c435', '1', '123', '5c31de155ece180a', 1);
insert into T_USER (id, user_code, user_name, password, state, remark, salt, dept_id)
values (35, '333', '444', '202cb962ac59075b964b07152d234b70', '0', '444', null, 1);
insert into T_USER (id, user_code, user_name, password, state, remark, salt, dept_id)
values (48, 'zw22', 'zw22', '202cb962ac59075b964b07152d234b70', '0', '123', null, null);
insert into T_USER (id, user_code, user_name, password, state, remark, salt, dept_id)
values (61, 'admin', 'admin', '202cb962ac59075b964b07152d234b70', '0', '123', 'e147292a0df28271', 2);
insert into T_USER (id, user_code, user_name, password, state, remark, salt, dept_id)
values (47, 'zw11', 'zw', '202cb962ac59075b964b07152d234b70', '1', 'zw', null, null);
commit;

