package com.lee.seckillshop.provider;

import com.lee.seckillshop.form.UserUpdateForm;
import org.apache.ibatis.jdbc.SQL;
import org.apache.solr.common.StringUtils;

import java.util.*;

public class UserUpdateProvider {

    public String updateUser(UserUpdateForm form){

        return new SQL(){
            {
                UPDATE("user_tb");

                if (!StringUtils.isEmpty(form.getPhone())) {
                    SET("phone = #{phone}");
                }
                if (!StringUtils.isEmpty(form.getEmail())) {
                    SET("email= #{email}");
                }
                if ((Integer)form.getStatus()!=null) {
                    SET("status= #{status}");
                }
                if ((Integer)form.getIdentity()!=null) {
                    SET("identity= #{identity}");
                }
                WHERE("id = #{id}" );

            }
        }.toString();
    }
}
