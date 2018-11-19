package com.lee.seckillshop.mapper;

import com.lee.seckillshop.model.Order;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Service;

@Mapper
public interface OrderMapper {
    @Insert("insert into order_tb(id,user_id,total_price) values(#{id},#{user.id},#{totalPrice})")
    public int saveOrder(Order order);
    @Update("update order_tb set status=#{status} where id=#{orderId}")
    public int updateState(String orderId,Integer status);
    @Select("select * from order_tb where id=#{id}")
    public Order findById(String id);
}
