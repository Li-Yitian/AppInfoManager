package cn.sp.appinfo.service.BackendUserService.impl;

import cn.sp.appinfo.dao.BackendUserMapper;
import cn.sp.appinfo.pojo.BackendUser;
import cn.sp.appinfo.service.BackendUserService.BackendUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 后端用户
 */
@Service("backendUserService")
public class BackendUserServiceImpl implements BackendUserService {
    @Autowired
    private BackendUserMapper backendUserMapper;
    /**
     *后端用户登录
     */
    @Override
    public BackendUser loginBackendUser(String userCode, String password) {
        BackendUser backendUser = backendUserMapper.getBackendUserByuserCodeAndPassword(userCode,password);
        return backendUser;
    }
}
