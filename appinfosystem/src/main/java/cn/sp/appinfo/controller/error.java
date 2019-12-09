package cn.sp.appinfo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/error")
public class error {
    @RequestMapping("/403")
    public String error403(){
        return "403";
    }
}
