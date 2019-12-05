package cn.sp.appinfo.dao;

import cn.sp.appinfo.pojo.AppVersion;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface AppVersionMapper {
    //查询指定AppId的所有APK版本信息
    public List<AppVersion> getAppVersionByid(Integer id);
    //添加APK版本信息
    public Integer addAppVersion(AppVersion appVersion);
    //查询对应appID的最新版本
    public AppVersion getNewAppVersionByappid(AppVersion appVersion);
    //删除APK版本信息
    public Integer deleteAppVersionByappid(Integer appid);
    //更新AppVersion
    public Integer updateAppVersion(AppVersion version);
    //APK文件地址更新为空
    public  Integer updateFilePathForNullById(Integer id);
}
