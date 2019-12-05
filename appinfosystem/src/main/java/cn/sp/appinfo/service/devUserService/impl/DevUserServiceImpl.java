package cn.sp.appinfo.service.devUserService.impl;

import cn.sp.appinfo.dao.DevUserMapper;
import cn.sp.appinfo.pojo.DevUser;
import cn.sp.appinfo.service.devUserService.DevUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class DevUserServiceImpl implements DevUserService {
    @Resource
private DevUserMapper devUserMapper;
    @Override

    public DevUser getDevUserByDevCodeAndPassword(String devCode, String password) {
        DevUser devUserByDevCodeAndPassword = devUserMapper.getDevUserByDevCodeAndPassword(devCode, password);
        return devUserByDevCodeAndPassword;
    }
}
