package com.lee.seckillshop.commons.vo;

import com.lee.seckillshop.commons.model.SeckillGood;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author admin
 * @date 2018-10-16
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SeckillGoodVo extends SeckillGood {
    private Long startTime;
    private Long finishedTime;
}
