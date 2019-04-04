package com.lee.seckillshop.commons.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MsgVo {
    private Integer from;
    private String fromName;
    private String msg;
    private Integer to;
}
