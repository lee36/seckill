package com.lee.seckillshop.mapper;

import com.lee.seckillshop.commons.model.ReciverInfo;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Service;

@Mapper
public interface ReciveInfoMapper {
    @Insert("Insert into reciver_info_tb(address,phone,order_id,type,user,sex) values(#{name},#{phone},#{orderId},#{type},#{user},#{sex})")
    public int addReciveInfo(ReciverInfo reciverInfo);
    @Select("select * from reciver_info_tb where order_id=#{orderId}")
    @Results({
            @Result(column = "address",property = "name")
    })
    ReciverInfo getRecive(String orderId);

    @Update("update reciver_info_tb set address=#{name},type=#{type},phone=#{phone},user=#{user},sex=#{sex} where id=#{id}")
    Boolean update(ReciverInfo reciverInfo);
}
