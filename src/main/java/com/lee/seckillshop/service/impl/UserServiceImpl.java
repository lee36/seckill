package com.lee.seckillshop.service.impl;

import com.lee.seckillshop.commons.componet.EmailComponet;
import com.lee.seckillshop.commons.componet.JedisComponet;
import com.lee.seckillshop.commons.model.Store;
import com.lee.seckillshop.commons.util.CommonUtil;
import com.lee.seckillshop.commons.util.JsonUtil;
import com.lee.seckillshop.commons.util.JwtUtil;
import com.lee.seckillshop.commons.util.PasswordUtil;
import com.lee.seckillshop.commons.vo.ResultResponse;
import com.lee.seckillshop.form.UserLoginForm;
import com.lee.seckillshop.form.UserRegistForm;
import com.lee.seckillshop.form.UserUpdateForm;
import com.lee.seckillshop.mapper.StoreMapper;
import com.lee.seckillshop.mapper.UserMapper;
import com.lee.seckillshop.commons.model.User;
import com.lee.seckillshop.commons.properties.WxLoginProperties;
import com.lee.seckillshop.service.UserService;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.apache.commons.math3.ml.neuralnet.MapUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.constraints.Pattern;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
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
    private JedisComponet jedisTemplate;
    @Autowired
    private StoreMapper storeMapper;
    @Autowired
    private EmailComponet emailComponet;
    @Override
    public Map<String, Object> inferUserExits(String openId, String access_token) throws Exception {
        Map<String, Object> map = new HashMap<>();
        User user = userMapper.findByOpenId(openId);
        if (user == null) {
            //添加这个用户
            Map userInfo = getUserWxInfo(access_token, openId);
            //解析map
            String nickName = (String) userInfo.get("nickname");
            String province = (String) userInfo.get("province");
            String city = (String) userInfo.get("city");
            String country = (String) userInfo.get("country");
            nickName = new String(nickName.getBytes("ISO8859-1"), "UTF-8");
            province = new String(province.getBytes("ISO8859-1"), "UTF-8");
            city = new String(city.getBytes("ISO8859-1"), "UTF-8");
            country = new String(country.getBytes("ISO8859-1"), "UTF-8");
            int sex = (int) userInfo.get("sex");
            String headImg = (String) userInfo.get("headimgurl");
            User wxUser = new User();
            wxUser.setHeadImg(headImg);
            wxUser.setOpenId(openId);
            wxUser.setSex(sex);
            wxUser.setNickname(nickName);
            wxUser.setIdentity(4);
            wxUser.setAddress(province + "-" + city + "-" + country);
            userMapper.saveUser(wxUser);
            String token2 = JwtUtil.genaratorToken(wxUser);
            map.put("user", wxUser);
            map.put("token", token2);
            return map;
        } else {
            String token1 = JwtUtil.genaratorToken(user);
            map.put("user", user);
            map.put("token", token1);
            if(user.getStatus()==1){
                map.put("error",1);
            }
            return map;
        }
    }

    @Override
    public Map<String, Object> userLogin(UserLoginForm user) throws IOException {
        String username = user.getUsername();
        User exits = userMapper.findByUsername(username);
        HashMap<String, Object> map = new HashMap<>();
        if (exits != null) {
            String dbPass = exits.getPassword();
            String salt = exits.getSalt();
            if (PasswordUtil.validtePassword(dbPass, user.getPassword(), salt)) {
                jedisTemplate.userVisited();
                map.put("user", exits);
                String token = JwtUtil.genaratorToken(exits);
                map.put("token", token);
                return map;
            }
            return null;
        }
        return null;
    }

    @Override
    public User userRegist(UserRegistForm user) throws Exception {
        User register = new User();
        BeanUtils.copyProperties(user, register);
        String password = user.getEnsurePassword().getPassword();
        String salt = CommonUtil.generateUUID();
        register.setPassword(PasswordUtil.passwordEncode(password, salt));
        register.setSalt(salt);
        register.setIdentity(1);
        userMapper.saveUser(register);
        return register;
    }

    private Map getUserWxInfo(String access_token, String openId) throws Exception {
        String userInfoUrl = wxLoginProperties.getUserInfoUrl();
        String realUserInfoUrl = String.format(userInfoUrl, access_token, openId);
        String str = restTemplate.getForObject(realUserInfoUrl, String.class);
        Map map = JsonUtil.json2Obj(str, Map.class);
        return map;
    }

    @Override
    public User findById(Integer id) {
        return userMapper.findById(id);
    }

    @Override
    @Transactional
    public Boolean becomeSeller(Integer id) {
        int seller = userMapper.becomeSeller(id);
        if(seller>0){
            //生成自己的店铺
            Store store = new Store();
            store.setInfo("这个人很懒什么都没写");
            //随机生成名字
            store.setName(CommonUtil.generateUUID());
            User user = new User();
            user.setId(id);
            store.setUser(user);
            storeMapper.addStore(store);
            return true;
        }
        return false;
    }

    @Override
    public User sysUserLogin(UserLoginForm loginForm) {
        String username=loginForm.getUsername();
        User user=userMapper.sysLogin(username);
        if(user!=null){
            String salt = user.getSalt();
            String dbPassword=user.getPassword();
            if(PasswordUtil.validtePassword(dbPassword,loginForm.getPassword(),salt)){

               return user;
            }
           return null;
        }
        return null;
    }

    @Override
    public Integer countUsers() throws Exception {
        Integer num=jedisTemplate.get("sys_users_num",Integer.class);
        if(num==null||num==0){
            Integer users = userMapper.coutUsers();
            jedisTemplate.set("sys_users_num",users,1*60L);
            return users;
        }
        return num;
    }

    @Override
    public Boolean sendEmail(HttpSession session, String email) throws MessagingException {
        //通过email查找用户
        User user = userMapper.findByEmail(email);
        if(user!=null){
            //找到了
            String token = emailComponet.sendEmail(email);
            session.setAttribute("forget-token",token);
            session.setAttribute("forget-email",email);
            return true;
        }
        return false;
    }

    @Override
    public Boolean checkeEmailToken(HttpSession session,String token) {
        String sessionToken = (String)session.getAttribute("forget-token");
        if(StringUtils.isEmpty(sessionToken)){
            return false;
        }
        return sessionToken.equals(token)?true:false;
    }

    @Override
    public Boolean comfireNewPass(HttpServletRequest request,String password, String rePassword) {
        if(!password.equals(rePassword)){
            return false;
        }
        //从数据中取出
        String email=(String)request.getSession().getAttribute("forget-email");
        if(StringUtils.isEmpty(email)){
            return false;
        }
        //通过email查找用户
        User user = userMapper.findByEmail(email);
        String salt=user.getSalt();
        //通过盐加密更新
        String dbPass = PasswordUtil.passwordEncode(password, salt);
        Integer integer = userMapper.updatePassByEmail(dbPass, email);
        if(integer==1){
            return true;
        }
        return false;
    }

    @Override
    public List<User> userList() {
        List<User> userList = userMapper.userList();
        return userList;
    }

    @Override
    @Transactional
    public Boolean delUsers(List<Integer> ids) {
        try {
            ids.stream().forEach(one -> {
                userMapper.deleteById(one);
            });
            return true;
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public List<Map<String, Object>> groupByCity() {
        return userMapper.groupByCity();
    }

    @Override
    @Transactional
    public Boolean updateUserInfo(UserUpdateForm form) {
        int i = userMapper.updateUserInfo(form);
        if(i>0){
            return true;
        }
        throw new RuntimeException("更新失败");

    }

    @Override
    @Transactional
    public Boolean cageUser(List<Integer> ids,Integer flag) {
        UserUpdateForm form = new UserUpdateForm();
        try {
            if (flag == 0) {
                for (Integer num : ids) {
                    form.setId(num);
                    form.setStatus(0);
                    userMapper.updateUserInfo(form);
                }
            } else {
                for (Integer num : ids) {
                    form.setId(num);
                    form.setStatus(1);
                    userMapper.updateUserInfo(form);
                }
            }
            return true;
        }catch (Exception e){
            return false;
        }


    }
}
