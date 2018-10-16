package com.lee.seckillshop.intercepter;

import com.lee.seckillshop.util.JsonUtil;
import com.lee.seckillshop.vo.ResultResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author admin
 * @date 2018-10-12
 */
@Component
public class TokenInterceper implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getParameter("token");
        if(token==null){
            ResultResponse result = new ResultResponse(503, "请登陆", null);
            String s = JsonUtil.obj2Json(result);
            response.getWriter().write(s);
            return false;
        }
        return true;
    }
}
