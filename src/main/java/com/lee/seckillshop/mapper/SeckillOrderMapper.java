package com.lee.seckillshop.mapper;

import com.lee.seckillshop.model.SeckillGood;
import com.lee.seckillshop.model.SeckillOrder;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.zookeeper.Op;

/**
 * @author admin
 * @date 2018-10-18
 */
@Mapper
public interface SeckillOrderMapper {
    @Insert("insert into seckill_order_tb(id,name,price,user_id) values(#{id},#{name},#{price},#{user.id})")
    public int saveSeckillOrder(SeckillOrder seckillOrder);
}
