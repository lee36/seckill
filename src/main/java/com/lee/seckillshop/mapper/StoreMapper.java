package com.lee.seckillshop.mapper;

import com.lee.seckillshop.commons.model.Store;
import org.apache.ibatis.annotations.*;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author admin
 * @date 2018-09-30
 */
@Mapper
public interface StoreMapper {
    @Select("select * from store_tb where id=#{id}")
    @Results({
            @Result(column = "user_id",property = "user",one=@One(
                    select = "com.lee.seckillshop.mapper.UserMapper.findById"
            ))
    })
    public Store findById(Integer id);

    @Insert("insert into store_tb(name,info,weight,user_id) values(#{name},#{info},#{weight},#{user.id})")
    public int addStore(Store store);

    @Select("select * from store_tb where user_id=#{id}")
    public Store findByUserId(Integer id);

    @Update("update store_tb set name=#{name},info=#{info} where id=#{id}")
    Integer updateStore(Store stroe);
}
