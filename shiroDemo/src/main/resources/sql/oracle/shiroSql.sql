create table T_DEPT
(
  id        NUMBER not null,
  dept_name VARCHAR2(20),
  parent_id NUMBER,
  state     CHAR(1),
  remark    VARCHAR2(100)
);
alter table T_DEPT add primary key (ID);
create table T_USER
(
  id        NUMBER not null,
  user_code VARCHAR2(20),
  user_name VARCHAR2(50),
  password  VARCHAR2(50),
  state     CHAR(1),
  remark    VARCHAR2(100),
  salt      VARCHAR2(50),
  dept_id   NUMBER
);
alter table T_USER add primary key (ID);

create sequence SEQ_T_DEPT;
create sequence SEQ_T_USER;

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

