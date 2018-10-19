package com.lee.seckillshop.vo;

import com.lee.seckillshop.model.SeckillGood;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.checkerframework.checker.nullness.qual.NonNull;
import reactor.util.annotation.NonNullApi;

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
