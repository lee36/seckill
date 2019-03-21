package com.lee.seckillshop.service;

import com.lee.seckillshop.form.UserLoginForm;
import com.lee.seckillshop.form.UserRegistForm;
import com.lee.seckillshop.commons.model.User;
import com.lee.seckillshop.form.UserUpdateForm;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author admin
 * @date 2018-09-20
 */
public interface UserService {
    public Map<String, Object> inferUserExits(String openId, String access_token) throws Exception;

    public Map<String, Object> userLogin(UserLoginForm user) throws IOException;

    public User userRegist(UserRegistForm user) throws Exception;

    public User findById(Integer id);

    public Boolean becomeSeller(Integer id);

    public User sysUserLogin(UserLoginForm loginForm);

    public Integer countUsers() throws Exception;

    public Boolean sendEmail(HttpSession session, String email) throws MessagingException;

    public Boolean checkeEmailToken(HttpSession session,String token);

    Boolean comfireNewPass(HttpServletRequest request,String password, String rePassword);

    public List<User> userList();

    public Boolean delUsers(List<Integer> ids);

    public List<Map<String,Object>> groupByCity();

    public Boolean updateUserInfo(UserUpdateForm form) throws RuntimeException;

    public Boolean cageUser(List<Integer> ids,Integer flag);
}
