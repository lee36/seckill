package com.lee.seckillshop.mapper;

import com.lee.seckillshop.model.User;
import com.lee.seckillshop.provider.UserInsertProvider;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Service;

/**
 * @author admin
 * @date 2018-09-20
 */
@Mapper
public interface UserMapper {

   @Select("select * from user_tb where open_id=#{openId}")
   public User findByOpenId(String openId);

   @Options(useGeneratedKeys = true,keyColumn = "id")
   @InsertProvider(type = UserInsertProvider.class,method = "save")
   public int saveUser(User user);

   @Select("select * from user_tb where username=#{username}")
   public User findByUsername(String username);

   @Select("select * from user_tb where phone=#{phone}")
   public User findByPhone(String phone);

   @Select("select * from user_tb where email=#{email}")
   public User findByEmail(String email);

   @Select("select * from user_tb where id=#{id}")
   public User findById(Integer id);
}
