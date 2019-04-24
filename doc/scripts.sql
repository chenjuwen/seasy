alter table ROLE_RESOURCE drop constraint FK_ROLE_RES_REF_ROLES;
alter table ROLE_RESOURCE drop constraint FK_ROLE_RES_REF_RES;
alter table USER_ROLE drop constraint FK_USER_ROLE_REF_USERS;
alter table USER_ROLE drop constraint FK_USER_ROLE_REF_ROLES;

drop table ROLE_RESOURCE cascade constraints;
drop table USER_ROLE cascade constraints;
drop table RESOURCES cascade constraints;
drop table ROLES cascade constraints;
drop table USERS cascade constraints;
drop table FILTER_CHAIN_DEFINS cascade constraints;
drop table MESSAGES cascade constraints;

drop sequence SEQ_USERS;
drop sequence SEQ_ROLES;
drop sequence SEQ_RESOURCES;
drop sequence SEQ_MESSAGES;



/*==============================================================*/
/* Table: RESOURCES                                             */
/*==============================================================*/
create table RESOURCES  (
   ID                   NUMBER(19)                      not null,
   RES_NO               VARCHAR2(20),
   RES_NAME             VARCHAR2(100),
   RES_URL              VARCHAR2(200),
   PARENT_ID            NUMBER(19),
   RES_IMG              VARCHAR2(50),
   OWNER                VARCHAR2(10),
   REMARKS              VARCHAR2(1000),
   OPERATOR             VARCHAR2(50),
   OPERATE_TIME         DATE,
   constraint PK_RESOURCES primary key (ID)
);

comment on table RESOURCES is
'菜单表';

comment on column RESOURCES.ID is
'ID';

comment on column RESOURCES.RES_NO is
'菜单编号';

comment on column RESOURCES.RES_NAME is
'菜单名称';

comment on column RESOURCES.RES_URL is
'菜单URL';

comment on column RESOURCES.PARENT_ID is
'上级菜单ID';

comment on column RESOURCES.RES_IMG is
'菜单图标';

comment on column RESOURCES.OWNER is
'所属子系统';

comment on column RESOURCES.REMARKS is
'备注';

comment on column RESOURCES.OPERATOR is
'操作人';

comment on column RESOURCES.OPERATE_TIME is
'操作时间';

/*==============================================================*/
/* Table: ROLES                                                 */
/*==============================================================*/
create table ROLES  (
   ROLE_ID              NUMBER(19)                      not null,
   ROLE_NO              VARCHAR2(10),
   ROLE_NAME            VARCHAR2(50)                    not null,
   ROLE_DESC            VARCHAR2(100),
   OPERATOR             VARCHAR2(50),
   OPERATE_TIME         DATE                           default SYSDATE,
   constraint PK_ROLES primary key (ROLE_ID)
);

comment on table ROLES is
'角色表';

comment on column ROLES.ROLE_ID is
'角色ID';

comment on column ROLES.ROLE_NO is
'角色编号';

comment on column ROLES.ROLE_NAME is
'角色名称';

comment on column ROLES.ROLE_DESC is
'角色描述';

comment on column ROLES.OPERATOR is
'操作人';

comment on column ROLES.OPERATE_TIME is
'操作时间';

/*==============================================================*/
/* Table: ROLE_RESOURCE                                         */
/*==============================================================*/
create table ROLE_RESOURCE  (
   ROLE_ID              NUMBER(19)                      not null,
   RES_ID               NUMBER(19)                      not null,
   constraint PK_ROLE_RESOURCE primary key (ROLE_ID, RES_ID)
);

comment on table ROLE_RESOURCE is
'角色_菜单表';

comment on column ROLE_RESOURCE.ROLE_ID is
'角色ID';

comment on column ROLE_RESOURCE.RES_ID is
'菜单ID';

/*==============================================================*/
/* Table: USERS                                                 */
/*==============================================================*/
create table USERS  (
   USER_ID              NUMBER(19)                      not null,
   LOGIN_NAME           VARCHAR2(20),
   USERNAME             VARCHAR2(50)                    not null,
   PASSWORD             VARCHAR2(100)                   not null,
   SALT                 VARCHAR2(200),
   PHONE                VARCHAR2(50),
   EMAIL                VARCHAR2(200),
   LOGIN_TIMES          INTEGER                        default 0,
   LAST_LOGIN_TIME      DATE,
   ENABLED              INTEGER                        default 1,
   OPERATOR             VARCHAR2(50),
   OPERATE_TIME         DATE                           default SYSDATE,
   constraint PK_USERS primary key (USER_ID)
);

comment on table USERS is
'用户表';

comment on column USERS.USER_ID is
'用户ID';

comment on column USERS.LOGIN_NAME is
'登录账号';

comment on column USERS.USERNAME is
'用户姓名';

comment on column USERS.PASSWORD is
'密码';

comment on column USERS.SALT is
'密码盐值';

comment on column USERS.PHONE is
'手机号';

comment on column USERS.EMAIL is
'邮箱地址';

comment on column USERS.LOGIN_TIMES is
'登录次数';

comment on column USERS.LAST_LOGIN_TIME is
'最后一次登录时间';

comment on column USERS.ENABLED is
'状态:0禁用,1启用';

comment on column USERS.OPERATOR is
'操作人';

comment on column USERS.OPERATE_TIME is
'操作时间';

/*==============================================================*/
/* Table: USER_ROLE                                             */
/*==============================================================*/
create table USER_ROLE  (
   USER_ID              NUMBER(19)                      not null,
   ROLE_ID              NUMBER(19)                      not null,
   constraint PK_USER_ROLE primary key (USER_ID, ROLE_ID)
);

comment on table USER_ROLE is
'用户_角色表';

comment on column USER_ROLE.USER_ID is
'用户ID';

comment on column USER_ROLE.ROLE_ID is
'角色ID';

alter table ROLE_RESOURCE
   add constraint FK_ROLE_RES_REF_ROLES foreign key (ROLE_ID)
      references ROLES (ROLE_ID);

alter table ROLE_RESOURCE
   add constraint FK_ROLE_RES_REF_RES foreign key (RES_ID)
      references RESOURCES (ID);

alter table USER_ROLE
   add constraint FK_USER_ROLE_REF_USERS foreign key (USER_ID)
      references USERS (USER_ID);

alter table USER_ROLE
   add constraint FK_USER_ROLE_REF_ROLES foreign key (ROLE_ID)
      references ROLES (ROLE_ID);




/*==============================================================*/
/* Table: FILTER_CHAIN_DEFINS                                   */
/*==============================================================*/
create table FILTER_CHAIN_DEFINS  (
   ID                   NUMBER(19)                      not null,
   CHAIN_NAME           VARCHAR2(200),
   CHAIN_DEFINITION     VARCHAR2(200),
   CHAIN_ORDER          NUMBER(8)                      default 0,
   constraint PK_FILTER_CHAIN_DEFINS primary key (ID)
);

comment on table FILTER_CHAIN_DEFINS is
'安全过滤链定义信息';

comment on column FILTER_CHAIN_DEFINS.ID is
'ID';

comment on column FILTER_CHAIN_DEFINS.CHAIN_NAME is
'CHAIN_NAME';

comment on column FILTER_CHAIN_DEFINS.CHAIN_DEFINITION is
'CHAIN_DEFINITION';

comment on column FILTER_CHAIN_DEFINS.CHAIN_ORDER is
'CHAIN_ORDER';





/*==============================================================*/
/* Table: MESSAGES                                              */
/*==============================================================*/
create table MESSAGES  (
   ID                   NUMBER(19)                      not null,
   TYPE                 VARCHAR2(15),
   CONTENTS             CLOB,
   SEND_USERID          NUMBER(19),
   RECEIVE_ID           CLOB,
   RECEIVE_YES          CLOB,
   DELETE_YES           CLOB,
   OPERATOR             VARCHAR2(50),
   OPERATE_TIME         DATE,
   constraint PK_MESSAGES primary key (ID)
);

comment on table MESSAGES is
'站内信';

comment on column MESSAGES.ID is
'ID';

comment on column MESSAGES.TYPE is
'类型.ALL, USERS, ROLES, ADMIN';

comment on column MESSAGES.CONTENTS is
'内容';

comment on column MESSAGES.SEND_USERID is
'发送人';

comment on column MESSAGES.RECEIVE_ID is
'接收用户ID';

comment on column MESSAGES.RECEIVE_YES is
'已接收用户ID';

comment on column MESSAGES.DELETE_YES is
'已删除用户ID';

comment on column MESSAGES.OPERATOR is
'操作人';

comment on column MESSAGES.OPERATE_TIME is
'操作时间';



create sequence SEQ_USERS
minvalue 1
start with 1000
increment by 1;


create sequence SEQ_ROLES
minvalue 1
start with 1000
increment by 1;


create sequence SEQ_RESOURCES
minvalue 1
start with 1000
increment by 1;


create sequence SEQ_FILTER_CHAIN_DEFINS
minvalue 1
start with 1000
increment by 1;


create sequence SEQ_MESSAGES
minvalue 1
start with 1000
increment by 1;

