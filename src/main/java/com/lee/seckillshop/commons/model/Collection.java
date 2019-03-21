package com.lee.seckillshop.commons.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Collection {
    private Integer id;
    private Goods goods;
    private User user;
    private String goodName;
    private String goodImg;
    private Timestamp createTime;
}
