package cn.sp.appinfo.config;

import cn.sp.appinfo.pojo.BackendUser;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class BackendLoginHandler implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        BackendUser backendUser = (BackendUser)request.getSession().getAttribute("userSession");
        if( backendUser != null){
            return true;
        }
        response.sendRedirect("/error/403");
        return false;
    }
}
