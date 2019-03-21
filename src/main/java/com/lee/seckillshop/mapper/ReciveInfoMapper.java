package com.lee.seckillshop.mapper;

import com.lee.seckillshop.commons.model.ReciverInfo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ReciveInfoMapper {
    @Insert("Insert into reciver_info_tb(address,telephone,order_id,type " +
            "values(#{address},#{telephone},#{orderId},#{type}))")
    public int addReciveInfo(ReciverInfo reciverInfo);
}
