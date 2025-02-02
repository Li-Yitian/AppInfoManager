package cn.sp.appinfo.controller.backendUserController;

import cn.sp.appinfo.pojo.BackendUser;
import cn.sp.appinfo.service.BackendUserService.BackendUserService;
import org.assertj.core.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/manager")
public class BackendUserLoginController {

    @Autowired
    private BackendUserService backendUsers;

    //去管理员登录页面
    @GetMapping("/login")
    public String doLogin() {
        return "jsp/backendlogin";
    }

    //登录
    @RequestMapping("/doLogin")
    public String doLogin(String userCode, String userPassword, HttpSession session, Model model) {
        System.out.println(userCode + "===" + userPassword);
        BackendUser all = userCode != null || userPassword != null ? backendUsers.loginBackendUser(userCode.trim(), userPassword) : null;
        System.out.println(all + "=-=-=-=-=-");
        if (all == null) {
            model.addAttribute("error", "账户或密码错误");
            return "jsp/backendlogin";
        } else {
            session.setAttribute("userSession", all);
            session.setMaxInactiveInterval(100000);
            return "jsp/backend/main";
        }

    }

    //注销
    @RequestMapping("/logout")
    public String loginOut(HttpSession session) {
        session.invalidate();
        return "jsp/backendlogin";
    }

}
