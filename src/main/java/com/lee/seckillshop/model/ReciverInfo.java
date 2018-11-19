package com.lee.seckillshop.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReciverInfo {
    private Integer id;
    private String address;
    private String telephone;
    private String orderId;
    private Integer type;
}
