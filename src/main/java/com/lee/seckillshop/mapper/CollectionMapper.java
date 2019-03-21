package com.lee.seckillshop.mapper;

import com.lee.seckillshop.commons.model.Collection;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface CollectionMapper {

    @Insert("Insert into collection_tb(good_id,user_id,good_name,good_img) values(#{goods.id},#{user.id},#{goodName},#{goodImg})")
    public int addCollection(Collection collection);

    @Delete("delete from collection_tb where id=#{id}")
    public int deleteById(Integer id);
}
