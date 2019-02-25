package com.lee.seckillshop.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;

/**
 * @author admin
 * @date 2018-09-19
 * `id` int auto_increment primary key comment '分类的id',
 * `name` varchar(100) comment '分类名称',
 * `info` varchar(255) comment '分类的介绍',
 * `create_time` timestamp on update current_timestamp default current_timestamp  comment '分类创建时间',
 * `update_time` timestamp on update current_timestamp default current_timestamp  comment '分类修改时间',
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoodsCatalog {
    private Integer id;
    private String name;
    private String info;
    private String icon;
    private Timestamp createTime;
    private Timestamp updateTime;
    private List<Goods> goodsList;
}
