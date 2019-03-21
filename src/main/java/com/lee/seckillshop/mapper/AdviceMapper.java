package com.lee.seckillshop.mapper;



import com.lee.seckillshop.commons.model.Advice;
import org.apache.ibatis.annotations.*;

import java.util.*;

public interface AdviceMapper {

    @Select("SELECT user_advice_tb.*,user_tb.`username`,user_tb.identity FROM user_tb,user_advice_tb WHERE user_tb.`id`=user_advice_tb.`from` AND user_advice_tb.state=0")
    public List<Map<String,Object>> adviceList();

    @Delete("delete from user_advice_tb where id=#{id}")
    public int deleteById(Integer id);

    @Select("select * from user_advice_tb where id=#{id}")
    public Advice getAdvice(Integer id);

    @Insert("Insert into user_advice_tb(content,`from`,state) values(#{content},#{from},#{state})")
    @Options(useGeneratedKeys = true,keyColumn = "id",keyProperty = "id")
    public int addAdvice(Advice advice);

    @Insert("insert into admin_repay_tb(publish,comment) values(#{publish},#{comment})")
    public int addAdminRepay(@Param("publish") Integer publish,@Param("comment") Integer comment);

}
