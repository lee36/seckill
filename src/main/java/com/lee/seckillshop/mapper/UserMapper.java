package com.lee.seckillshop.mapper;

import com.lee.seckillshop.commons.model.User;
import com.lee.seckillshop.form.UserUpdateForm;
import com.lee.seckillshop.provider.UserInsertProvider;
import com.lee.seckillshop.provider.UserUpdateProvider;
import org.apache.ibatis.annotations.*;

import java.util.*;

/**
 * @author admin
 * @date 2018-09-20
 */
@Mapper
public interface UserMapper {

    @Select("select * from user_tb where open_id=#{openId}")
    public User findByOpenId(String openId);

    @Options(useGeneratedKeys = true, keyColumn = "id")
    @InsertProvider(type = UserInsertProvider.class, method = "save")
    public int saveUser(User user);

    @Select("select * from user_tb where username=#{username}")
    public User findByUsername(String username);

    @Select("select * from user_tb where phone=#{phone}")
    public User findByPhone(String phone);

    @Select("select * from user_tb where email=#{email}")
    public User findByEmail(String email);

    @Select("select * from user_tb where id=#{id}")
    public User findById(Integer id);

    @Update("update user_tb set identity=2 where id=#{id}")
    public int becomeSeller(Integer id);

    @Select("select * from user_tb where username=#{username} and identity in (2,3)")
    public User sysLogin(String username);

    @Select("select count(*) from user_tb")
    public Integer coutUsers();

    @Update("update user_tb set password=#{password} where email=#{email}")
    public Integer updatePassByEmail(@Param("password") String password,@Param("email") String email);

    @Select("select * from user_tb where identity in (1,2,4)")
    public List<User> userList();

    @Delete("delete from user_tb where id=#{id}")
    public int deleteById(Integer id);

    @Select("SELECT address as name,COUNT(*) AS value FROM user_tb GROUP BY address;")
    public List<Map<String,Object>> groupByCity();

    @UpdateProvider(type = UserUpdateProvider.class,method = "updateUser")
    int updateUserInfo(UserUpdateForm form);
}
