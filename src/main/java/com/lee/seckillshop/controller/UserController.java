package com.lee.seckillshop.controller;

import com.lee.seckillshop.form.UserLoginForm;
import com.lee.seckillshop.form.UserRegistForm;
import com.lee.seckillshop.model.User;
import com.lee.seckillshop.service.UserService;
import com.lee.seckillshop.util.JwtUtil;
import com.lee.seckillshop.util.ValidateErrorUtil;
import com.lee.seckillshop.vo.ResultResponse;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.Map;

/**
 * @author admin
 * @date 2018-09-25
 */
@RestController
@RequestMapping("/user")
public class UserController {

   @Autowired
   public UserService userService;

   @PostMapping("/login")
   public Object login(@Valid UserLoginForm user,BindingResult result){
       if(result.hasErrors()){
           Map<String, String> map = ValidateErrorUtil.buildeError(result);
           return  new ResultResponse(501,"输入格式有误",map);
       }
       Map<String,Object> exits = userService.userLogin(user);
       if(exits!=null){
           return new ResultResponse(0,"登陆成功",exits);
       }
       return new ResultResponse(502,"账号或者密码错误",null);
   }

   @PostMapping("/regist")
    public Object regist(@Valid UserRegistForm user, BindingResult result) throws Exception {
       if(result.hasErrors()){
           Map<String, String> map = ValidateErrorUtil.buildeError(result);
           return  new ResultResponse(501,"注册失败",map);
       }
       User register=userService.userRegist(user);
       if(register!=null){
           return new ResultResponse(0,"注册成功",register);
       }
       return new ResultResponse(502,"注册失败",null);
   }
    @RequestMapping("/parse")
    public Object parseToken(String token){
       if(StringUtils.isEmpty(token)){
           return new ResultResponse(501,"未登录",null);
       }
        Claims claims = JwtUtil.parseToken(token);
        if(claims==null){
            return new ResultResponse(501,"未登录",null);
        }
        return new ResultResponse(0,"解析成功",JwtUtil.parseToken(token));
    }

}
