package com.lee.seckillshop.controller;

import com.lee.seckillshop.commons.util.JwtUtil;
import com.lee.seckillshop.commons.util.ValidateErrorUtil;
import com.lee.seckillshop.form.UserLoginForm;
import com.lee.seckillshop.form.UserRegistForm;
import com.lee.seckillshop.commons.model.User;
import com.lee.seckillshop.service.UserService;
import com.lee.seckillshop.commons.vo.ResultResponse;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;
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
    public Object login(@Valid UserLoginForm user, BindingResult result) throws IOException {
        if (result.hasErrors()) {
            Map<String, String> map = ValidateErrorUtil.buildeError(result);
            return new ResultResponse(501, "输入账号或者密码格式有误", map);
        }
        Map<String, Object> exits = userService.userLogin(user);
        if (exits != null) {
            return new ResultResponse(0, "登陆成功", exits);
        }
        return new ResultResponse(502, "账号或者密码错误", null);
    }

    @PostMapping("/regist")
    public Object regist(@RequestBody @Valid UserRegistForm user, BindingResult result) throws Exception {
        if (result.hasErrors()) {
            Map<String, String> map = ValidateErrorUtil.buildeError(result);
            return new ResultResponse(501, "注册失败", map);
        }
        User register = userService.userRegist(user);
        if (register != null) {
            return new ResultResponse(0, "注册成功", register);
        }
        return new ResultResponse(502, "注册失败", null);
    }

    @RequestMapping("/parse")
    public Object parseToken(String token) {
        if (StringUtils.isEmpty(token)) {
            return new ResultResponse(501, "未登录", null);
        }
        Claims claims = JwtUtil.parseToken(token);
        if (claims == null) {
            return new ResultResponse(501, "未登录", null);
        }
        return new ResultResponse(0, "解析成功", JwtUtil.parseToken(token));
    }
    @GetMapping("/becomeSeller")
    public Object becomeSeller(Integer id){
        Boolean b = userService.becomeSeller(id);
        if(b){
            return new ResultResponse(200,"success",b);
        }
        return new ResultResponse(500,"error",b);
    }
}
