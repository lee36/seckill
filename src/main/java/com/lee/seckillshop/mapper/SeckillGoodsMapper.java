package com.lee.seckillshop.mapper;

import com.lee.seckillshop.commons.model.SeckillGood;
import com.lee.seckillshop.commons.vo.SeckillGoodVo;
import org.apache.ibatis.annotations.*;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author admin
 * @date 2018-10-08
 */
@Mapper
public interface SeckillGoodsMapper {

    @Select("SELECT * FROM seckill_good_tb LIMIT 4")
    public List<SeckillGoodVo> seckillGoodListTop4();

    @Select("select * from seckill_good_tb")
    public List<LinkedHashMap<String, Object>> seckillGoodsList();

    @Select("select * from seckill_good_tb where id=#{seckGoodId}")
    public SeckillGood findSeckillGood(Integer seckGoodId);

    @Update("update seckill_good_tb set stock=stock-1 where id=#{id}")
    public void updateStockWithOne(@Param("id") Integer id);

    @Insert("insert into seckill_good_tb(name,price,stock,img,create_time,end_time,store_id,good_id)" +
            "values(#{name},#{price},#{stock},#{img},#{createTime},#{endTime},#{store.id},#{goodId})")
    public int addSeckillGood(SeckillGood seckillGood);

    @Update("update seckill_good_tb set status=#{status} where id=#{id}")
    public int updateStatus(@Param("status") Integer status,@Param("id") Integer id);

    @Select("select * from seckill_good_tb where store_id=#{id}")
    public List<SeckillGood> getMySelfSeckill(Integer id);

    @Delete("delete from seckill_good_tb where id=#{id}")
    int deleteById(Integer id);

    @Select("select * from seckill_good_tb")
    @Results({
            @Result(column = "store_id",property = "store",one=
                    @One(select = "com.lee.seckillshop.mapper.StoreMapper.findById"))
    })
    List<SeckillGood> getIndexSeckill();
}
