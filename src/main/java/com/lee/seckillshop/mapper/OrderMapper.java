package com.lee.seckillshop.mapper;

import com.lee.seckillshop.commons.model.Order;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface OrderMapper {
    @Insert("insert into order_tb(id,user_id,total_price) values(#{id},#{user.id},#{totalPrice})")
    public int saveOrder(Order order);

    @Update("update order_tb set status=#{status} where id=#{orderId}")
    public int updateState(@Param("status") Integer status,@Param("orderId") String orderId);

    @Select("select * from order_tb where id=#{id}")
    public Order findById(String id);

    @Select("select * from order_tb where user_id=#{id}")
    @Results({
            @Result(column = "user_id",property = "user",one=@One(
                    select = "com.lee.seckillshop.mapper.UserMapper.findById"
            ))
    })
    List<Order> getMyOrder(Integer id);
}
