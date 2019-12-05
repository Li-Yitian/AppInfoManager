package cn.sp.appinfo.dao;

import cn.sp.appinfo.pojo.AppInfo;
import com.sun.xml.internal.bind.v2.schemagen.xmlschema.Appinfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * app信息
 */
@Repository
@Mapper
public interface AppInfoMapper {
    //读取app总数
    public Integer getAppinfoRows(@Param("app") AppInfo appinfo);
    //分页读取app信息
    public List<Appinfo> getAppInfoByPage(@Param("app") AppInfo appinfo,@Param("first") Integer first,@Param("pageSize") Integer pageSize);
    //添加App信息
    public Integer addAppInfo(AppInfo appInfo);
    //查询对应KAPKName
    public Integer getAPKNameByApkName(String apkName);
    //查询单行Appinfo数据
    public AppInfo getAppInfoById(Integer id);
    //更新信息
    public Integer updateAppInfo(AppInfo appinfo);
    //删除图片路径信息
    public Integer deleteAppLogo(Integer id);
    //删除指定APP信息
    public Integer deleteAppInfo(Integer id);


}
