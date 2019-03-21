package com.lee.seckillshop.commons.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Banner {
    private Integer id;
    private String bannerName;
    private Integer status;
    private Timestamp createTime;
    private Timestamp updateTime;
}
