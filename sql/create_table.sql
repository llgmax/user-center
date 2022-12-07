-- auto-generated definition
create table user
(
    tags         varchar(1024)     null comment '用户标签列表',
    userRole     int     default 0 not null comment '用户角色 0-普通用户 1-管理员',
    id           bigint auto_increment comment 'id'
        primary key,
    username     varchar(256)      null comment '用户昵称',
    userAccount  varchar(256)      null comment '账号',
    avatarUrl    varchar(1024)     null comment '用户头像',
    gender       tinyint           null comment '性别',
    userPassword varchar(512)      not null comment '密码',
    email        varchar(512)      null comment '邮箱',
    userStatus   int     default 0 null comment '状态 0-正常',
    phone        varchar(128)      null comment '电话',
    createTime   datetime          null comment '创建时间',
    updateTime   datetime          null comment '更新时间',
    isDelete     tinyint default 0 not null comment '是否删除',
    planetCode   varchar(512)      null comment '星球编号'
)
    comment '用户';

-- auto-generated definition
create table tag
(
    id         bigint auto_increment
        primary key,
    tagName    varchar(250)      null comment '标签名称',
    userId     bigint            null comment '用户ID',
    parentId   bigint            null comment '父标签id',
    isParent   tinyint           null comment '0 --不是，1--父标签',
    createTime datetime          null comment '创建时间',
    updateTime datetime          null comment '更新时间',
    isDelete   tinyint default 0 not null comment '是否删除',
    constraint tagName_uindex
        unique (tagName)
)
    comment '标签表';

create index tag_userId_index
    on tag (userId);