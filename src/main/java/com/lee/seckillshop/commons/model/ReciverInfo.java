package com.lee.seckillshop.commons.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReciverInfo {
    private Integer id;
    private String name;
    private String phone;
    private String orderId;
    private Integer type;
    private String user;
    private String sex;
}
