package com.interceptor;

import com.util.TokenUtil;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author 13049
 */
@Component
public class TokenInterceptor extends HandlerInterceptorAdapter {

    // 重写 前置拦截方法
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        // 1、从请求头中获取token
        if (HttpMethod.OPTIONS.toString().equals(request.getMethod())) {
            System.out.println("OPTIONS请求，放行");
            return true;
        }
        String token = request.getHeader("token");
        System.out.println(token);
        // 2、判断 token 是否存在
        if (token == null || "".equals(token)) {
            System.out.println("未登录");
            return false;
        }

        // 3、解析token
        if(!TokenUtil.verify(token)){
            return false;
        }

        return true;
    }
}