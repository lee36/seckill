package com.lee.seckillshop.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

/**
 * @author admin
 * @date 2018-09-19
 * create table if not exists `seckill_order_tb`(
 * `id` varchar(255) primary key comment '秒杀订单详情编号',
 * `name` varchar(255) comment '秒杀商品名称',
 * `num` int default 1 comment '购买数量',
 * `price` int comment '商品价格',
 * `user_id` int comment '下单用户的id(外键)',
 * `create_time` timestamp default current_timestamp  comment '下单时间',
 * INDEX idx(`id`),
 * INDEX useridx(`user_id`)
 * )ENGINE=INNODB DEFAULT CHARSET=utf8;
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SeckillOrder {
    private String id;
    private String name;
    private Integer num;
    private Integer price;
    private User user;
    private Timestamp createTime;
}
