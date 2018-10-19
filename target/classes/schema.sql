/**
用户表
 */
create table if not exists `user_tb`(
  `id` int auto_increment primary key comment '用户的id',
  `username` varchar(100) comment '用户名',
  `password` varchar(200) comment '用户密码',
  `nickname` varchar(200) comment '用户微信昵称',
  `head_img` varchar(255) comment '用户头像',
  `sex` int comment '用户性别:0为女性，1为男性',
  `address` varchar(200) comment '用户地址',
  `open_id` varchar(255) comment '微信的openId',
  `identity` int comment '用户的身份:1为顾客，2为商家',
  `salt` varchar(255) comment '密码加密所用的盐',
  `phone` varchar(50) comment '用户的手机号',
  `email` varchar(255) comment '用户的邮箱',
  `create_time` timestamp on update current_timestamp default current_timestamp   comment '用户创建时间',
  `update_time` timestamp on update current_timestamp default current_timestamp  comment '用户修改时间',
   INDEX idx(`id`),
   INDEX openidx(`open_id`)
)ENGINE=INNODB DEFAULT CHARSET=utf8;
/**
商品分类表
 */
create table if not exists `goods_catalog_tb`(
  `id` int auto_increment primary key comment '分类的id',
  `name` varchar(100) comment '分类名称',
  `info` varchar(255) comment '分类的介绍',
  `icon` varchar (255) comment '小图标',
  `create_time` timestamp on update current_timestamp default current_timestamp  comment '分类创建时间',
  `update_time` timestamp on update current_timestamp default current_timestamp  comment '分类修改时间',
   INDEX idx(`id`)
)ENGINE=INNODB DEFAULT CHARSET=utf8;
/**
商品表
 */
create table if not exists `goods_tb`(
  `id` int auto_increment primary key comment '商品的id',
  `name` varchar(255) comment '商品名称',
  `price` int comment '商品的价格(单位分)',
  `stock` int comment '商品的库存',
  `info` varchar(255) comment '商品描述',
  `detail` varchar (255) comment '商品详情',
  `img` varchar(255) comment '商品图片',
  `weight` int comment '热度',
  `create_time` timestamp on update current_timestamp default current_timestamp  comment '商品创建时间',
  `update_time` timestamp on update current_timestamp default current_timestamp  comment '商品修改时间',
  `store_id` int comment '店铺的id(外键),一个店铺多个商品',
  `catalog_id` int comment '分类的id(外键),一个分类多个商品',
   INDEX idx(`id`),
   INDEX storeidx(`store_id`)
)ENGINE=INNODB DEFAULT CHARSET=utf8;
/**
店铺表
 */
create table if not exists `store_tb`(
   `id` int auto_increment primary key comment '店铺id',
   `name` varchar(255) comment '店铺名称',
   `info` varchar(255) comment '店铺介绍',
   `weight` int comment '热度',
   `create_time` timestamp on update current_timestamp default current_timestamp  comment '商品创建时间',
   `update_time` timestamp on update current_timestamp default current_timestamp  comment '商品修改时间',
   `user_id` int comment '用户的id(外键),一个用户一个店铺',
    INDEX idx(`id`),
    INDEX useridx(`user_id`)
 )ENGINE=INNODB DEFAULT CHARSET=utf8;
/**
订单表
*/
create table if not exists `order_tb`(
    `id` varchar(255) primary key comment '订单编号',
    `create_time` timestamp on update current_timestamp default current_timestamp  comment '订单创建时间',
    `user_id` int comment '用户id(外键)表明谁下的单',
    `total_price` int comment '订单总金额(单位分)',
    `status` int default 2 comment '订单状态:1.支付，2未支付',
    `order_detail_id` int comment '订单详情id(外键)',
     INDEX idx(`id`),
     INDEX useridx(`user_id`)
)ENGINE=INNODB DEFAULT CHARSET=utf8;
/**
订单详情
*/
create table if not exists `order_detail_tb`(
   `id` varchar(255) primary key comment '订单详情编号',
   `name` varchar(255) comment '商品名称',
   `num` int comment '购买数量',
   `price` int comment '商品总价',
   `good_img` varchar(255) comment '商品图片',
    INDEX idx(`id`)
)ENGINE=INNODB DEFAULT CHARSET=utf8;
/**
秒杀商品表
 */
create table if not exists `seckill_good_tb`(
  `id` int auto_increment primary key comment '秒杀商品的id',
  `name` varchar(255) comment '秒杀商品名称',
  `price` int comment '秒杀商品的价格(单位分)',
  `stock` int comment '秒杀商品的库存',
  `img` varchar(255) comment '秒杀商品图片',
  `create_time` timestamp  default current_timestamp  comment '秒杀商品创建时间',
  `update_time` timestamp on update current_timestamp default current_timestamp  comment '秒杀商品修改时间',
  `end_time` timestamp  comment '秒杀商品结束时间',
  `status` int default 0 comment '0代表未开始,1代表正在秒杀,2代表秒杀结束',
  `store_id` int comment '店铺的id(外键),一个店铺多个秒杀商品',
   INDEX idx(`id`),
   INDEX storeidx(`store_id`)
)ENGINE=INNODB DEFAULT CHARSET=utf8;
/**
秒杀订单表
 */
create table if not exists `seckill_order_tb`(
   `id` varchar(255) primary key comment '秒杀订单详情编号',
   `name` varchar(255) comment '秒杀商品名称',
   `num` int default 1 comment '购买数量',
   `price` int comment '商品价格',
   `user_id` int comment '下单用户的id(外键)',
   `create_time` timestamp default current_timestamp  comment '下单时间',
   `address` varchar(255) comment '收货地址',
   `status` int default 2 comment '1代表支付,2代表未支付',
   INDEX idx(`id`),
   INDEX useridx(`user_id`)
)ENGINE=INNODB DEFAULT CHARSET=utf8;
/**
收藏表
 */
create table if not exists `collection_tb`(
   `id` int auto_increment primary key comment '收藏表id',
   `good_id` int comment '商品的id(外键)',
   `user_id` int comment '用户的id(外键)',
   `good_name` varchar(255) comment '商品名称',
   `good_img` varchar(255) comment '商品图片',
   `create_time` timestamp default current_timestamp  comment '收藏时间',
    INDEX idx(`id`),
    INDEX useridx(`user_id`)
)ENGINE=INNODB DEFAULT CHARSET=utf8;
/**
每日流量表
 */
create table if not exists `flow_tb`(
  `id` int auto_increment primary key comment '流量表id',
  `num` bigint comment '访问量',
  `login_time` TIMESTAMP comment '登陆时间',
  INDEX idx(`id`)
)ENGINE=INNODB DEFAULT CHARSET=utf8;