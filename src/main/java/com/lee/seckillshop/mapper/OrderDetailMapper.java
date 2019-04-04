package com.lee.seckillshop.mapper;

import com.lee.seckillshop.commons.model.OrderDetail;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface OrderDetailMapper {
    @Insert("insert into order_detail_tb(name,num,price,good_img,order_id) values(#{name},#{num},#{price},#{goodImg},#{order.id})")
    public int saveOrderDetail(OrderDetail orderDetail);
    @Select("select * from order_detail_tb where order_id=#{id}")
    List<OrderDetail> findDetailsByOrderId(String id);
}
