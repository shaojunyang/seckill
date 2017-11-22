--数据库初始化脚本
--创建数据库
CREATE DATABASE seckill;
--打开数据库
use seckill;
--创建秒杀库存表
CREATE TABLE seckill(
    `seckill_id` bigint NOT NULL auto_increment comment "商品库存id",
    `name` varchar(100) NOT NULL COMMENT "商品名称",
    `number` int not null comment "库存数量",
    `create_time` timestamp not null DEFAULT  CURRENT_TIMESTAMP COMMENT '创建时间',=
    `start_time` timestamp NOT NULL comment "秒杀开始时间",
    `end_time` timestamp not null comment "秒杀结束时间",
    primary key(seckill_id),
    key idx_start_time(start_time),
    key idx_end_time(end_time),
    key idx_create_time(create_time)
)ENGINE=INNODB auto_increment=1000 DEFAULT CHARSET=utf8 COMMENT '秒杀库存表';

--初始化数据
insert into
    seckill(name,number,start_time,end_time)
values("1000元秒杀iphone6",100,'2017-11-01 00:00:00','2017-11-30 00:00:00'),
("500元秒杀ipad",200,'2017-11-01 00:00:00','2017-11-30 00:00:00'),
("300元秒杀小米4",300,'2017-11-01 00:00:00','2017-11-30 00:00:00'),
("200元秒杀红米note",400,'2017-11-01 00:00:00','2017-11-30 00:00:00');

--秒杀成功明细表
--用户登录认证相关的信息
create table success_killed(
      `seckill_id` bigint not null comment "秒杀商品id",
      `user_phone` bigint not null comment "用户手机号",
      `state` tinyint not null default -1 comment "状态表示，-1表示无效，0 表示成功，1-已付款，2-已发货",
      `create_time` timestamp not null DEFAULT CURRENT_TIMESTAMP comment "创建时间",
      primary key(seckill_id,user_phone),/* 联合主键*/
      key idx_create_time(create_time)
)ENGINE=INNODB auto_increment=1000 DEFAULT CHARSET=utf8 COMMENT '秒杀成功明细表';



--连接数据库








