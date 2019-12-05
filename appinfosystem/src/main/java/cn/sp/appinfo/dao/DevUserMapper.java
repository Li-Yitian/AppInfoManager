package cn.sp.appinfo.dao;

import cn.sp.appinfo.pojo.AppInfo;
import cn.sp.appinfo.pojo.DevUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 开发者用户数据访问层
 */
@Repository
@Mapper
public interface DevUserMapper {
    //读取用户信息，根据用户编码与密码
    public DevUser getDevUserByDevCodeAndPassword(@Param("devCode") String devCode, @Param("password") String password);

}
