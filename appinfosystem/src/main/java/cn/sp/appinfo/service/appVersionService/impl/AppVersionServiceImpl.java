package cn.sp.appinfo.service.appVersionService.impl;

import cn.sp.appinfo.dao.AppInfoMapper;
import cn.sp.appinfo.dao.AppVersionMapper;
import cn.sp.appinfo.pojo.AppInfo;
import cn.sp.appinfo.pojo.AppVersion;
import cn.sp.appinfo.service.appVersionService.AppVersionService;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.util.Date;
import java.util.List;

@Service
public class AppVersionServiceImpl implements AppVersionService {
    @Resource
    private AppVersionMapper appVersionMapper;
    @Resource
    private AppInfoMapper appInfoMapper;

    /**
     * 获取指定APP的APK版本
     *
     * @param id
     * @return
     */
    @Override
    public List<AppVersion> getAppVersionListByid(Integer id) {
        List<AppVersion> appVersion = appVersionMapper.getAppVersionByid(id);
        return appVersion;
    }

    /**
     * 添加新的APK版本信息,更新Appinfo最新版本id
     *
     * @param appVersion
     * @return
     */
    @Override
    public Boolean addAppVersion(AppVersion appVersion) {
        if (appVersionMapper.addAppVersion(appVersion) > 0) {
            AppInfo appInfo = new AppInfo();
            appInfo.setId(appVersion.getAppId());
//            appInfo.setDevId(appInfo.getCreatedBy());
            appInfo.setVersionId(appVersionMapper.getNewAppVersionByappid(appVersion).getId());
            if (appInfoMapper.updateAppInfo(appInfo) > 0)
                return true;
        }
        return false;
    }

    /**
     * 获取最新的App配置
     *
     * @return
     */
    @Override
    public AppVersion getNewAppVersionByAppid(AppVersion appVersion) {
        AppVersion newAppVersionByappid = appVersionMapper.getNewAppVersionByappid(appVersion);
        return newAppVersionByappid;
    }

    /**
     * 更新版本信息
     *
     * @param version
     * @return
     */
    @Override
    public Boolean updateAppVersion(AppVersion version) {
        version.setModifyDate(new Date());
        if (appVersionMapper.updateAppVersion(version) > 0) {
                return true;
        }
        return false;
    }

    /**
     * APK文件地址更新为空
     */
    public Boolean deleteFilePathForNullById(Integer id) {
        if (appVersionMapper.updateFilePathForNullById(id) > 0) {
            return true;
        }
        return false;
    }
}
