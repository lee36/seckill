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
public class Advice {
    private Integer id;
    private String content;
    private Integer from;
    private Timestamp createTime;
    private Timestamp updateTime;
    private Integer state;
}
