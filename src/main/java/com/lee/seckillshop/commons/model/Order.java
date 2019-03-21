package com.lee.seckillshop.commons.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

/**
 * @author admin
 * @date 2018-09-19
 * create table if not exists `order_tb`(
 * `id` varchar(255) primary key comment '订单编号',
 * `create_time` timestamp on update current_timestamp default current_timestamp  comment '订单创建时间',
 * `user_id` int comment '用户id(外键)表明谁下的单',
 * `total_price` int comment '订单总金额(单位分)',
 * `status` int comment '订单状态:1.支付，2未支付',
 * `order_detail_id` int comment '订单详情id(外键)',
 * INDEX idx(`id`),
 * INDEX useridx(`user_id`)
 * )ENGINE=INNODB DEFAULT CHARSET=utf8;
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    private String id;
    private Timestamp createTime;
    private User user;
    private Integer totalPrice;
    private Integer status;
}
