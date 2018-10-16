package com.lee.seckillshop.mapper;

import com.lee.seckillshop.model.SeckillGood;
import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author admin
 * @date 2018-10-08
 */
@Mapper
public interface SeckillGoodsMapper {
    @Select("SELECT * FROM seckill_good_tb LIMIT 4")
    public List<SeckillGood> seckillGoodList();
}
