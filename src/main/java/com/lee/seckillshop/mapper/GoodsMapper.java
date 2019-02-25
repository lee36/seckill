package com.lee.seckillshop.mapper;

import com.lee.seckillshop.model.Goods;
import com.lee.seckillshop.model.Store;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * @author admin
 * @date 2018-09-30
 */
@Mapper
public interface GoodsMapper {
    @Select("SELECT * FROM goods_tb ORDER BY weight")
    @Results({
            @Result(column = "store_id", property = "store", javaType = Store.class, one = @One(
                    select = "com.lee.seckillshop.mapper.StoreMapper.findById"
            ))
    })
    public List<Goods> findTop4ByWeight();

    @Select("select * from goods_tb")
    @Results({
            @Result(column = "store_id", property = "store", one = @One(
                    select = "com.lee.seckillshop.mapper.StoreMapper.findById"
            ))
    })
    public List<Goods> findAllGoods();

    @Select("select * from goods_tb where id=#{id}")
    @Results({
            @Result(column = "store_id", property = "store", one = @One(
                    select = "com.lee.seckillshop.mapper.StoreMapper.findById"
            ))
    })
    public Goods findById(Integer id);
}
