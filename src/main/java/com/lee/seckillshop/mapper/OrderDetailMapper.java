package com.lee.seckillshop.mapper;

import com.lee.seckillshop.model.OrderDetail;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;

@Mapper
public interface OrderDetailMapper {
    @Insert("insert into order_detail_tb(name,num,price,good_img,order_id) values(#{name},#{num},#{price},#{goodImg},#{order.id})")
    public int saveOrderDetail(OrderDetail orderDetail);
}
