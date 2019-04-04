package com.lee.seckillshop.mapper;

import com.lee.seckillshop.commons.model.Collection;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CollectionMapper {

    @Insert("Insert into collection_tb(good_id,user_id,good_name,good_img) values(#{goods.id},#{user.id},#{goodName},#{goodImg})")
    public int addCollection(Collection collection);

    @Delete("delete from collection_tb where id=#{id}")
    public int deleteById(Integer id);

    @Select("select * from collection_tb where user_id=#{id}")
    @Results({
            @Result(column = "good_id",property = "goods",many = @Many(
                    select = "com.lee.seckillshop.mapper.GoodsMapper.findById"
            ))
    })
    List<Collection> getMyCollectors(Integer id);
}
