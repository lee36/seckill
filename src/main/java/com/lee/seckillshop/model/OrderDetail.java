package com.lee.seckillshop.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author admin
 * @date 2018-09-19
create table if not exists `order_detail_tb`(
`id` varchar(255) primary key comment '订单详情编号',
`name` varchar(255) comment '商品名称',
`num` int comment '购买数量',
`price` int comment '商品总价',
`good_img` varchar(255) comment '商品图片',
INDEX idx(`id`),
INDEX orderidx(`order_id`)
)ENGINE=INNODB DEFAULT CHARSET=utf8;
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetail {
   private Integer id;
   private String name;
   private Integer num;
   private Integer price;
   private String goodImg;
   private Order order;
}
