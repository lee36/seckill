package com.lee.seckillshop.commons.intercepter;

import com.lee.seckillshop.commons.componet.JedisComponet;
import com.lee.seckillshop.commons.util.IpUtils;
import com.lee.seckillshop.commons.util.JsonUtil;
import com.lee.seckillshop.commons.vo.ResultResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author admin
 * @date 2018-09-27
 */
@Component
public class RegistIntercepter implements HandlerInterceptor {
    @Autowired
    private JedisComponet jedisTemplate;
    private static final Integer SECONDVISITED = 1;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //获取ip地址
        String ip = IpUtils.getIpAddr(request);
        Integer num = jedisTemplate.get(ip, Integer.class);
        if (num == null) {
            jedisTemplate.set(ip, 1, 3);
            return true;
        }
        if (SECONDVISITED.equals(num)) {
            ResultResponse resultResponse = new ResultResponse(505, "你操作过快请休息下", null);
            String s = JsonUtil.obj2Json(resultResponse);
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(s);
            return false;
        }
        Long incr = jedisTemplate.incr(ip);
        return true;
    }

}
