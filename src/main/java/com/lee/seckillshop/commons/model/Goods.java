package com.lee.seckillshop.commons.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;

/**
 * @author admin
 * @date 2018-09-19
 * `id` int auto_increment primary key comment '商品的id',
 * `name` varchar(255) comment '商品名称',
 * `price` int comment '商品的价格(单位分)',
 * `stock` int comment '商品的库存',
 * `info` varchar(255) comment '商品描述',
 * `img` varchar(255) comment '商品图片',
 * `create_time` timestamp on update current_timestamp default current_timestamp  comment '商品创建时间',
 * `update_time` timestamp on update current_timestamp default current_timestamp  comment '商品修改时间',
 * `store_id` int comment '店铺的id(外键),一个店铺多个商品',
 * INDEX idx(`id`),
 * INDEX storeidx(`store_id`)
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Goods {
    private Integer id;
    private String name;
    private Integer price;
    private Integer stock;
    private String info;
    private String detail;
    private Integer weight;
    private String img;
    private Timestamp createTime;
    private Timestamp updateTime;
    private Integer status;
    private Store store;
    private List<User> userList;
    private GoodsCatalog goodsCatalog;
}
