package cn.sp.appinfo.service.appVersionService;

import cn.sp.appinfo.pojo.AppVersion;

import java.util.List;

public interface AppVersionService {
    //查询指定APK版本信息
    public List<AppVersion> getAppVersionListByid(Integer id);
    //添加APK版本信息
    public Boolean addAppVersion(AppVersion appVersion);
    //获取最新APP的APK版本信息
    public AppVersion getNewAppVersionByAppid(AppVersion appVersion);
    //更新AppVersion
    public Boolean updateAppVersion(AppVersion version);
    //APK文件地址更新为空
    public  Boolean deleteFilePathForNullById(Integer id);
}
