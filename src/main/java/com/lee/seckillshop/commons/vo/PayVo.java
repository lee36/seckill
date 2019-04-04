package com.lee.seckillshop.commons.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PayVo {
    private Integer flag;
    private List<Integer> goodIds;
    private List<Integer> nums;
    private Integer money;
    private Integer userId;
}
