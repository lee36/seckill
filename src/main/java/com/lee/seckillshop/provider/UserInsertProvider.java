package com.lee.seckillshop.provider;

import com.lee.seckillshop.model.User;
import org.apache.ibatis.jdbc.SQL;

/**
 * @author admin
 * @date 2018-09-27
 */
public class UserInsertProvider {

   public String save(User user){
       return new SQL(){
           {
               INSERT_INTO("user_tb");
               if(user.getPassword()!=null){
                   VALUES("password","#{password}");
               }
               if(user.getHeadImg()!=null){
                   VALUES("head_img","#{headImg}");
               }
               if(user.getIdentity()!=null){
                   VALUES("identity","#{identity}");
               }
               if(user.getOpenId()!=null){
                   VALUES("open_id","#{openId}");
               }
               if(user.getSalt()!=null){
                   VALUES("salt","#{salt}");
               }
               if(user.getAddress()!=null){
                   VALUES("address","#{address}");
               }
               if(user.getEmail()!=null){
                   VALUES("email","#{email}");
               }
               if(user.getNickname()!=null){
                   VALUES("nickname","#{nickname}");
               }
               if(user.getPhone()!=null){
                   VALUES("phone","#{phone}");
               }
               if(user.getSex()!=null){
                   VALUES("sex","#{sex}");
               }
               if(user.getUsername()!=null){
                   VALUES("username","#{username}");
               }
           }
       }.toString();
   }
}
