package cn.sp.appinfo.config;

import cn.sp.appinfo.pojo.DevUser;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class DevLoginHandlerInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        DevUser devUser = (DevUser)request.getSession().getAttribute("devUserSession");
        if (devUser != null) {
            return true;
        }
        response.sendRedirect("/error/403");
        return false;
}
}
