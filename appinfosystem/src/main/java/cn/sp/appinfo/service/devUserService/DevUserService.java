package cn.sp.appinfo.service.devUserService;

import cn.sp.appinfo.pojo.DevUser;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;


public interface DevUserService {
    /**
     * 登录
     * @param devCode
     * @param password
     * @return
     */
    public DevUser getDevUserByDevCodeAndPassword(@Param("devCode") String devCode, @Param("password") String password);

}
