package com.lee.seckillshop.mapper;

import com.lee.seckillshop.model.GoodsCatalog;
import com.sun.org.apache.xml.internal.resolver.Catalog;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author admin
 * @date 2018-10-08
 */
@Mapper
public interface CatalogMapper {
//    @Select("select * from goods_catalog_tb")
//    @Results({
//            @Result(column = "id",property = "id",id = true),
//            @Result(column = "id",javaType =List.class,property = "goodsList",many = @Many(
//                    select = "com.lee.seckillshop.mapper.GoodsMapper.findTop4ByWeightOrderByDesc"
//            ))
//    })
//    public List<GoodsCatalog> findAllGoodsCatalog();
    @Select("select * from goods_catalog_tb")
    public List<GoodsCatalog> findAllCatalog();
}
