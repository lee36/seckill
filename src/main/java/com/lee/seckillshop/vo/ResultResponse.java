package com.lee.seckillshop.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author admin
 * @date 2018-09-25
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultResponse {
    private int code;
    private String msg;
    private Object data;
}
