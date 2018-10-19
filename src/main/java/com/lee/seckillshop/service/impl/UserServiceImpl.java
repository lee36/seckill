package com.lee.seckillshop.service.impl;

import com.lee.seckillshop.componet.JedisTemplate;
import com.lee.seckillshop.form.UserLoginForm;
import com.lee.seckillshop.form.UserRegistForm;
import com.lee.seckillshop.mapper.UserMapper;
import com.lee.seckillshop.model.User;
import com.lee.seckillshop.properties.WxLoginProperties;
import com.lee.seckillshop.service.UserService;
import com.lee.seckillshop.util.CommonUtil;
import com.lee.seckillshop.util.JsonUtil;
import com.lee.seckillshop.util.JwtUtil;
import com.lee.seckillshop.util.PasswordUtil;
import io.netty.util.collection.CharObjectMap;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author admin
 * @date 2018-09-20
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private WxLoginProperties wxLoginProperties;
    @Autowired
    private JedisTemplate jedisTemplate;

    @Override
    public Map<String,Object> inferUserExits(String openId,String access_token) throws Exception {
        Map<String, Object> map = new HashMap<>();
        User user = userMapper.findByOpenId(openId);
        if(user==null){
            //添加这个用户
            Map userInfo = getUserWxInfo(access_token,openId);
            //解析map
            String nickName=(String)userInfo.get("nickname");
            String province=(String)userInfo.get("province");
            String city=(String)userInfo.get("city");
            String country=(String)userInfo.get("country");
            nickName= new String(nickName.getBytes("ISO8859-1"), "UTF-8");
            province= new String(province.getBytes("ISO8859-1"), "UTF-8");
            city= new String(city.getBytes("ISO8859-1"), "UTF-8");
            country=new String(country.getBytes("ISO8859-1"), "UTF-8");
            int sex= (int) userInfo.get("sex");
            String headImg= (String) userInfo.get("headimgurl");
            User wxUser = new User();
            wxUser.setHeadImg(headImg);
            wxUser.setOpenId(openId);
            wxUser.setSex(sex);
            wxUser.setNickname(nickName);
            wxUser.setAddress(province+"-"+city+"-"+country);
            userMapper.saveUser(wxUser);
            String token2 = JwtUtil.genaratorToken(wxUser);
            map.put("user",wxUser);
            map.put("token",token2);
            return map;
        }else{
           String token1=JwtUtil.genaratorToken(user);
            map.put("user",user);
            map.put("token",token1);
            return map;
        }
    }

    @Override
    public Map<String,Object> userLogin(UserLoginForm user) {
        String username=user.getUsername();
        User exits= userMapper.findByUsername(username);
        HashMap<String,Object> map=new HashMap<>();
        if(exits!=null){
            String dbPass = exits.getPassword();
            String salt= exits.getSalt();
            if(PasswordUtil.validtePassword(dbPass,user.getPassword(),salt)){
                jedisTemplate.userVisited();
                map.put("user",exits);
                String token = JwtUtil.genaratorToken(exits);
                map.put("token",token);
                return map;
            }
            return null;
        }
        return null;
    }

    @Override
    public User userRegist(UserRegistForm user) throws Exception{
        User register = new User();
        BeanUtils.copyProperties(user,register);
        String password=user.getEnsurePassword().getPassword();
        String salt=CommonUtil.generateUUID();
        register.setPassword(PasswordUtil.passwordEncode(password,salt));
        register.setSalt(salt);
        register.setIdentity(1);
        userMapper.saveUser(register);
        return register;
    }

    private Map getUserWxInfo(String access_token,String openId) throws Exception {
        String userInfoUrl = wxLoginProperties.getUserInfoUrl();
        String realUserInfoUrl = String.format(userInfoUrl, access_token,openId);
        String str = restTemplate.getForObject(realUserInfoUrl, String.class);
        Map map = JsonUtil.json2Obj(str, Map.class);
        return map;
    }

    @Override
    public User findById(Integer id){
        return userMapper.findById(id);
    }
}
