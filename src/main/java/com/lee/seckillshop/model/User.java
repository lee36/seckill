package com.lee.seckillshop.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.List;

/**
 * @author admin
 * @date 2018-09-19
 *`id` int auto_increment primary key comment '用户的id',
 *   `username` varchar(100) comment '用户名',
 *   `password` varchar(200) comment '用户密码',
 *   `head_img` varchar(255) comment '用户头像',
 *   `sex` int comment '用户性别:1为男性，2为女性',
 *   `address` varchar(200) comment '用户地址',
 *   `open_id` varchar(255) comment '微信的openId',
 *   `identity` int comment '用户的身份:1为顾客，2为商家',
 *   `salt` varchar(255) comment '密码加密所用的盐',
 *   `phone` bigint comment '用户的手机号',
 *   `email` varchar(255) comment '用户的邮箱',
 *   `create_time` timestamp on update current_timestamp default current_timestamp   comment '用户创建时间',
 *   `update_time` timestamp on update current_timestamp default current_timestamp  comment '用户修改时间',
 *    INDEX idx(`id`),
 *    INDEX openidx(`open_id`)
 * */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

   private Integer id;
   private String username;
   private String nickname;
   private String password;
   private String headImg;
   private Integer sex;
   private String address;
   private String openId;
   private Integer identity;
   private String salt;
   private String phone;
   private String email;
   private Timestamp createTime;
   private Timestamp updateTime;
   private List<Goods> goodsList;

}
