package com.lee.seckillshop.mapper;

import com.lee.seckillshop.model.Store;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * @author admin
 * @date 2018-09-30
 */
@Mapper
public interface StoreMapper {
    @Select("select * from store_tb where id=#{id}")
    public Store findById(Integer id);
}
