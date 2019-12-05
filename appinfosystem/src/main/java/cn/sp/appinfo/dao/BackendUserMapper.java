package cn.sp.appinfo.dao;

import cn.sp.appinfo.pojo.BackendUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 后端用户类
 */
@Repository
@Mapper
public interface BackendUserMapper {
    /**
     *根据用户编码与密码查询用户
     */
    public BackendUser getBackendUserByuserCodeAndPassword(@Param("userCode") String userCode, @Param("userPassword") String password);
}
