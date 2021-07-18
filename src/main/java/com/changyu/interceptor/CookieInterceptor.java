package com.changyu.interceptor;

import com.changyu.po.User;
import com.changyu.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieInterceptor implements HandlerInterceptor {
    final String COOKIE_USERNAME = "cookie_username";

    @Autowired
    RedisUtil redisUtil;

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {
        if(request.getSession().getAttribute("user") == null) {
            Cookie[] cookies = request.getCookies();
            if(null != cookies) {
                for(Cookie item : cookies) {
                    if(COOKIE_USERNAME.equals(item.getName())) {
                        String userName = item.getValue();
                        if(redisUtil.hasKey(userName)) {
                            User user = (User) redisUtil.get(userName);
                            request.getSession().setAttribute("user", user);
                            return true;
                        }
                    }
                }
            }
        }
        return true;
    }
}
