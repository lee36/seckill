package com.lee.seckillshop.controller;

import com.lee.seckillshop.model.User;
import com.lee.seckillshop.properties.WxLoginProperties;
import com.lee.seckillshop.service.UserService;
import com.lee.seckillshop.util.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

/**
 * @author admin
 * @date 2018-09-20
 */
@Controller
@RequestMapping("/wx")
public class WxController {

    @Autowired
    private WxLoginProperties wxLoginProperties;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private UserService userService;

    @RequestMapping("/login_url")
    @ResponseBody
    public Object getWxLoginUrl(@RequestParam("access_page") String access_page){
        System.out.println(1111);
        String loginUrl = wxLoginProperties.getLoginUrl();
        String realLoginUrl = String.format(loginUrl, wxLoginProperties.getAppId(),
                wxLoginProperties.getRedictUrl(),access_page);
        System.out.println(realLoginUrl+"====");
        return realLoginUrl;
    }
    @RequestMapping("/login/back")
    public Object LoginBack(@RequestParam("code") String code,
                            @RequestParam("state") String state) throws Exception {
        String accessTokenUrl = wxLoginProperties.getAccessTokenUrl();
        String realAccessTokenUrl = String.format(accessTokenUrl, wxLoginProperties.getAppId(),
                wxLoginProperties.getAppSecret(), code);
        String str = restTemplate.getForObject(realAccessTokenUrl, String.class);
        Map map = JsonUtil.json2Obj(str, Map.class);
        String access_token =(String)map.get("access_token");
        String openId=(String)map.get("openid");
        //通过openId判断是否用户存在
        Map userMap = userService.inferUserExits(openId, access_token);
        return "redirect:http://10.1.0.88:8080/"+state+"?token="+userMap.get("token");
    }
}
