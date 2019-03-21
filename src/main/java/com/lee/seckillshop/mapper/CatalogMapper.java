package com.lee.seckillshop.mapper;

import com.lee.seckillshop.commons.model.GoodsCatalog;
import com.lee.seckillshop.provider.CatalogProvider;
import com.sun.javafx.font.t2k.T2KFactory;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author admin
 * @date 2018-10-08
 */
@Mapper
public interface CatalogMapper {

    @Select("select * from goods_catalog_tb")
    public List<GoodsCatalog> findAllCatalog();

    @Insert("Insert into goods_catalog_tb(name,info,icon) values(#{name},#{info},#{icon})")
    public int addCatalog(GoodsCatalog catalog);

    @Delete("delete from goods_catalog_tb where id=#{id}")
    public int deleteById(int id);

    @Select("select * from goods_catalog_tb where id=#{id}")
    GoodsCatalog getCatalog(Integer id);

    @UpdateProvider(type = CatalogProvider.class,method = "update")
    int updateCatalog(GoodsCatalog catalog);
}
