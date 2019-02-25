package com.lee.seckillshop.mapper;

import com.lee.seckillshop.model.SeckillGood;
import com.lee.seckillshop.vo.SeckillGoodVo;
import org.apache.ibatis.annotations.*;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author admin
 * @date 2018-10-08
 */
@Mapper
public interface SeckillGoodsMapper {
    @Select("SELECT * FROM seckill_good_tb LIMIT 4")
    public List<SeckillGoodVo> seckillGoodListTop4();

    @Select("select * from seckill_good_tb")
    public List<LinkedHashMap<String, Object>> seckillGoodsList();

    @Select("select * from seckill_good_tb where id=#{seckGoodId}")
    public SeckillGood findSeckillGood(Integer seckGoodId);

    @Update("update seckill_good_tb set stock=stock-1 where id=#{id}")
    public void updateStockWithOne(@Param("id") Integer id);
}
