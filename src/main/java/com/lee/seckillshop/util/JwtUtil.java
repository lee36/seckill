package com.lee.seckillshop.util;

import com.lee.seckillshop.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.util.StringUtils;

import java.util.Date;

/**
 * @author admin
 * @date 2018-09-20
 */
public class JwtUtil {
    private static final String SUBJECT="lee";
    private static final Long EXPRETIME=6*24*60*60*100L;
    private static final String APPSECRET="lee36";

    /**
     * 生成jwt字符串
     * @param user
     * @return
     */
   public static String genaratorToken(User user){

       String token=Jwts.builder().setSubject(SUBJECT).setIssuedAt(new Date()).setExpiration(new Date(System.currentTimeMillis()+EXPRETIME))
               .claim("id",user.getId()).claim("identity",user.getIdentity()).claim("headImg",user.getHeadImg())
               .claim("openId",user.getOpenId()).
                       claim("username",user.getUsername()).claim("sex",user.getSex()).
                       claim("phone",user.getPhone()).claim("email",user.getEmail()).claim("nickname",user.getNickname()).signWith(SignatureAlgorithm.HS256,APPSECRET).compact();
       return token;
   }

    /**
     * 解析token字符串
     * @param token
     * @return
     */
   public static Claims parseToken(String token){
       if(StringUtils.isEmpty(token)){
           return null;
       }
       try {
           return Jwts.parser().setSigningKey(APPSECRET).
                   parseClaimsJws(token).getBody();
       }catch (Exception e) {
           return null;
       }
   }

}
