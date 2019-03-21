package com.lee.seckillshop.mapper;

import com.lee.seckillshop.commons.model.Goods;
import com.lee.seckillshop.commons.model.GoodsCatalog;
import com.lee.seckillshop.commons.model.Store;
import com.lee.seckillshop.provider.UpdateGoodsProvider;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author admin
 * @date 2018-09-30
 */
@Mapper
public interface GoodsMapper {
    @Select("SELECT * FROM goods_tb where status=0 ORDER BY weight")
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
            )),@Result(column = "catalog_id", property = "goodsCatalog", one = @One(
            select = "com.lee.seckillshop.mapper.CatalogMapper.getCatalog"
    )),
    })
    public Goods findById(Integer id);


    @Select("SELECT * FROM goods_tb WHERE store_id=" +
            "(SELECT id FROM store_tb WHERE user_id=#{id})")
    @Results({
            @Result(column = "catalog_id",property ="goodsCatalog",
                    javaType = GoodsCatalog.class,one = @One(select = "com.lee.seckillshop.mapper.CatalogMapper.getCatalog"))
    })
    List<Goods> getMySelfGoodsList(Integer id);

    @Insert("Insert into goods_tb(name,price,stock,info,detail,img,store_id,catalog_id) " +
            "values(#{name},#{price},#{stock},#{info},#{detail},#{img},#{store.id},#{goodsCatalog.id})")
    int addGoods(Goods goods);

    @UpdateProvider(type = UpdateGoodsProvider.class,method = "update")
    int updateGoods(Goods goods);


    @Delete("Delete from goods_tb where id=#{id}")
    void deleteById(Integer id);
}
