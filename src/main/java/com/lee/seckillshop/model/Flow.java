package com.lee.seckillshop.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.Date;

/**
 * @author admin
 * @date 2018-09-19
create table if not exists `flow_tb`(
`id` int auto_increment primary key comment '流量表id',
`num` bigint comment '访问量',
`user_id` int comment '用户的id(外键)',
`login_time` TIMESTAMP comment '登陆时间',
`ip` varchar(255) comment '登陆ip',
INDEX idx(`id`)
)ENGINE=INNODB DEFAULT CHARSET=utf8;
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Flow {
   private Integer id;
   private Long num;
   private Timestamp loginTime;
}
