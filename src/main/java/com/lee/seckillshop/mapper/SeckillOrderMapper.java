package com.lee.seckillshop.mapper;

import com.lee.seckillshop.commons.model.SeckillOrder;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author admin
 * @date 2018-10-18
 */
@Mapper
public interface SeckillOrderMapper {
    @Insert("insert into seckill_order_tb(id,name,price,user_id,seckill_good_id) values(#{id},#{name},#{price},#{user.id},#{good.id})")
    public int saveSeckillOrder(SeckillOrder seckillOrder);

    @Select("select * from seckill_order_tb where id=#{id}")
    @Results({
            @Result(column = "seckill_good_id",property = "good",one = @One(
                    select = "com.lee.seckillshop.mapper.SeckillGoodsMapper.findSeckillGood"
            ))
    })
    public SeckillOrder findById(String id);

    @Update("update seckill_order_tb set status=#{status} where id=#{out_trade_no}")
    int updateState(@Param("out_trade_no") String out_trade_no,@Param("status") int status);

    @Select("select * from seckill_order_tb where user_id=#{id}")
    @Results({
            @Result(column = "user_id",property = "user",one=@One(
                    select = "com.lee.seckillshop.mapper.UserMapper.findById"
            ))
    })
    public List<SeckillOrder> getMySeckillOrder(Integer id);
}
