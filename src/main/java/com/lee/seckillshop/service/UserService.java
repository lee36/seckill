package com.lee.seckillshop.service;

import com.lee.seckillshop.form.UserLoginForm;
import com.lee.seckillshop.form.UserRegistForm;
import com.lee.seckillshop.model.User;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * @author admin
 * @date 2018-09-20
 */
public interface UserService {
    public Map<String,Object> inferUserExits(String openId, String access_token) throws Exception;
    public Map<String,Object> userLogin(UserLoginForm user);
    public User userRegist(UserRegistForm user) throws Exception;
}
