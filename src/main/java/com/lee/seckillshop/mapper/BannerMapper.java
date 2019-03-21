package com.lee.seckillshop.mapper;

import com.lee.seckillshop.commons.model.Banner;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface BannerMapper {
    @Insert("insert into banner_tb(banner_name,status) values(#{bannerName},#{status})")
    public int addBanner(Banner banner);

    @Select("Select * from banner_tb")
    public List<Banner> getAllBanners();

    @Delete("delete from banner_tb where id=#{id}")
    public int deleteById(Integer id);

    @Update("update banner_tb set status=1 where id=#{id}")
    public int realse(int id);

    @Update("update banner_tb set status=0 where id=#{id}")
    public int cage(int id);

}
