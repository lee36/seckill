package com.lee.seckillshop.intercepter;

import com.lee.seckillshop.util.JsonUtil;
import com.lee.seckillshop.util.JwtUtil;
import com.lee.seckillshop.vo.ResultResponse;
import io.jsonwebtoken.Claims;
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
        String param_token = request.getParameter("token");
        String head_token = request.getHeader("token");
        if (param_token == null || JwtUtil.parseToken(param_token) == null) {
            if (head_token == null || JwtUtil.parseToken(head_token) == null) {
                ResultResponse result = new ResultResponse(503, "请登陆", null);
                String s = JsonUtil.obj2Json(param_token);
                response.getWriter().write(s);
                return false;
            } else {
                return true;
            }
        }
        return true;
    }
}
