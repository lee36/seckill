package com.lee.seckillshop.mapper;

import com.lee.seckillshop.model.SeckillGood;
import com.lee.seckillshop.model.SeckillOrder;
import org.apache.ibatis.annotations.*;
import org.apache.zookeeper.Op;

/**
 * @author admin
 * @date 2018-10-18
 */
@Mapper
public interface SeckillOrderMapper {
    @Insert("insert into seckill_order_tb(id,name,price,user_id) values(#{id},#{name},#{price},#{user.id})")
    public int saveSeckillOrder(SeckillOrder seckillOrder);
    @Select("select * from seckill_order_tb")
    public SeckillOrder findById(String id);
    @Update("update seckill_order_tb set status=#{status} where id=#{out_trade_no}")
    int updateState(String out_trade_no, int status);
}
