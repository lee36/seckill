package com.lee.seckillshop.model;

import javafx.util.converter.TimeStringConverter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;

/**
 * @author admin
 * @date 2018-09-19
 * create table if not exists `seckill_good_tb`(
 * `id` int auto_increment primary key comment '秒杀商品的id',
 * `name` varchar(255) comment '秒杀商品名称',
 * `price` int comment '秒杀商品的价格(单位分)',
 * `stock` int comment '秒杀商品的库存',
 * `img` varchar(255) comment '秒杀商品图片',
 * `create_time` timestamp on update current_timestamp default current_timestamp  comment '秒杀商品创建时间',
 * `update_time` timestamp on update current_timestamp default current_timestamp  comment '秒杀商品修改时间',
 * `store_id` int comment '店铺的id(外键),一个店铺多个秒杀商品',
 * INDEX idx(`id`),
 * INDEX storeidx(`store_id`)
 * )ENGINE=INNODB DEFAULT CHARSET=utf8;
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SeckillGood {
    private Integer id;
    private String name;
    private Integer price;
    private Integer stock;
    private String img;
    private Timestamp createTime;
    private Timestamp updateTime;
    private Timestamp endTime;
    private int status;
    private Store store;
}
