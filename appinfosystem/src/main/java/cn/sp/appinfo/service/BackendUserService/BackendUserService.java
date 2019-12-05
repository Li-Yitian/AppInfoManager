package cn.sp.appinfo.service.BackendUserService;

import cn.sp.appinfo.pojo.BackendUser;
import org.apache.ibatis.annotations.Param;

public interface BackendUserService {
    /**
     *后端用户登录
     */
    public BackendUser loginBackendUser(@Param("userCode") String userCode, @Param("userPassword") String password);

}

