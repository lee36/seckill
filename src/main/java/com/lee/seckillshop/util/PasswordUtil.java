package com.lee.seckillshop.util;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * @author admin
 * @date 2018-09-25
 */
public class PasswordUtil {

   public static String passwordEncode(String formPass,String salt){
       String bytes = DigestUtils.md5Hex(formPass + salt);
       return bytes;
   }

   public static boolean validtePassword(String dbPass,String formPass,String salt){
     String valid=DigestUtils.md5Hex(formPass+salt);
     if(dbPass.equals(valid)){
         return true;
     }
     return false;
   }
}
