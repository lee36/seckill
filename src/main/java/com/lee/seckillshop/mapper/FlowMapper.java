package com.lee.seckillshop.mapper;

import com.lee.seckillshop.model.Flow;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author admin
 * @date 2018-09-28
 */
@Mapper
public interface FlowMapper {
    @Insert("insert into flow_tb(num) values(#{num})")
    public int saveFlow(Flow flow);
}
