package cn.sp.appinfo.controller.backendUserController;

import cn.sp.appinfo.pojo.BackendUser;
import cn.sp.appinfo.service.BackendUserService.BackendUserService;
import org.assertj.core.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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

    @RequestMapping("/doLogin")
    public String doLogin(String userCode, String userPassword, HttpSession session, Model model) {
        BackendUser all = backendUsers.loginBackendUser(userCode.trim(), userPassword);
        System.out.println(all);
        if (all == null) {
            System.out.println(all);
            model.addAttribute("error", "账户或密码错误");
            return "jsp/backendlogin";
        } else {
            session.setAttribute("userSession", all);
            return "jsp/backend/main";
        }

    }

    @RequestMapping("/logout")
    public String loginOut(HttpSession session) {
        session.invalidate();
        return "jsp/backendlogin";
    }

}
