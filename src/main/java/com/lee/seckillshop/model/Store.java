package com.lee.seckillshop.model;

import javafx.util.converter.TimeStringConverter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

/**
 * @author admin
 * @date 2018-09-19
 * `id` int auto_increment primary key comment '店铺id',
 *    `name` varchar(255) comment '店铺名称',
 *    `info` varchar(255) comment '店铺介绍',
 *    `create_time` timestamp on update current_timestamp default current_timestamp  comment '商品创建时间',
 *    `update_time` timestamp on update current_timestamp default current_timestamp  comment '商品修改时间',
 *    `user_id` int comment '用户的id(外键),一个用户一个店铺',
 *     INDEX idx(`id`),
 *     INDEX useridx(`user_id`)
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Store {
   private Integer id;
   private String name;
   private String info;
   private Timestamp createTime;
   private Timestamp updateTime;
   private User user;
}
