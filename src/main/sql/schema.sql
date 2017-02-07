--数据库初始化脚本

--创建数据库
CREATE TABLE seckill;
--使用数据库
use seckill;
--创建秒杀表
CREATE TABLE seckill(
`seckill_id` bigint NOT NULL AUTO_INCREMENT COMMENT '商品库存id',
`name` VARCHAR(120) NOT NULL COMMENT '商品名称',
`number` int NOT NULL COMMENT '库存数量',
`start_time` TIMESTAMP NOT NULL COMMENT '秒杀开启时间',
`end_time` TIMESTAMP NOT NULL COMMENT '秒杀结束时间',
`create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '秒杀创建时间',
PRIMARY KEY (seckill_id),
KEY idx_start_time(start_time),
KEY idx_end_time(end_time),
KEY idx_create_time(create_time)
)ENGINE=InnoDB AUTO_INCREMENT =1000 DEFAULT CHARSET=UTF-8 COMMENT '秒杀库存表';

--初始化数据
INSERT into seckill(name,number,start_time,end_time)
VALUES
  ('1000元秒杀iphone6',100,'2016-10-26 12:00:00','2016-10-26 14:00:00'),
('500元秒杀iphone5',200,'2016-10-26 12:00:00','2016-10-26 14:00:00'),
('300元秒杀小米4',300,'2016-10-26 12:00:00','2016-10-26 14:00:00'),
('200元秒杀红米',400,'2016-10-26 12:00:00','2016-10-26 14:00:00');

--创建秒杀明细表
--用户登录认证相关信息
CREATE  table success_kill (
   `seckill_id` bigint NOT NULL COMMENT '秒杀商品id',
   `user_phone` bigint NOT NULL COMMENT '用户手机号码',
   `state` tinyint NOT NULL DEFAULT -1 COMMENT '状态标识：-1：无效，0：有效，1：已付款，2：已收货',
  `create_time` TIMESTAMP NOT NULL COMMENT '订单创建时间',
  PRIMARY KEY (seckill_id,user_phone),/*联合主键*/
  KEY idx_create_time(create_time)
)ENGINE=InnoDB DEFAULT CHARSET=UTF8 COMMENT='秒杀成功明细表';

--连接数据库控制台
mysql -uroot -p568355