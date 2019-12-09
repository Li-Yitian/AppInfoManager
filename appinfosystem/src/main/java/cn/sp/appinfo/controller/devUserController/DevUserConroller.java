package cn.sp.appinfo.controller.devUserController;

import cn.sp.appinfo.pojo.DevUser;
import cn.sp.appinfo.service.devUserService.DevUserService;
import org.apache.jasper.tagplugins.jstl.core.Out;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/dev")
public class DevUserConroller {
    @Resource
    private DevUserService dev;

    //去登录页面
    @RequestMapping("/login")
    public String toLogin(){
        return "jsp/devlogin";
    }
    //登录
    @RequestMapping("/dologin")
    public  String login(String devCode, String devPassword, HttpSession session){
        DevUser devUser = dev.getDevUserByDevCodeAndPassword(devCode, devPassword);

        if(devUser != null){
            session.setAttribute("devUserSession",devUser);
            session.setMaxInactiveInterval(100000);
            return "jsp/developer/main";
        }else {
            session.setAttribute("error","账号或密码错误！");
            return "jsp/devlogin";
        }
    }

    /**
     * 退出登录
     * @param session
     * @return
     */
    @RequestMapping("/logout")
    public String loginOut(HttpSession session) {
        session.invalidate();
        return "jsp/devlogin";
    }
}
