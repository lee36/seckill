package com.lee.seckillshop.mapper;

import com.lee.seckillshop.commons.model.Flow;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author admin
 * @date 2018-09-28
 */
@Mapper
public interface FlowMapper {

    @Insert("insert into flow_tb(num) values(#{num})")
    public int saveFlow(Flow flow);

    @Select("SELECT login_time,num FROM flow_tb GROUP BY login_time DESC LIMIT 0,6")
    public List<Flow> findlast6Flow();
}
