package cn.sp.appinfo.service.appinfoService;


import cn.sp.appinfo.pojo.AppInfo;
import cn.sp.appinfo.tool.Page;

import java.sql.SQLException;

public interface AppinfoService {
    /**
     *分页显示所有appinfo
     */
    public void getAppinfoByPage(AppInfo appinfo,Page page);
    /**
     * 添加App信息
     */
    public Boolean addAppInfo(AppInfo appInfo);
    /**
     * 判断APKName
     */
    public Integer apkexist(String apkName);

    /**查询单行Appinfo数据
     *
     * @param id
     * @return
     */
    public AppInfo getAppInfoById(Integer id);

    /**更新信息
     *
     * @param appinfo
     * @return
     */
    public Boolean updateAppInfo(AppInfo appinfo) throws SQLException;

    /**删除图片路径信息
     *
     * @param id
     * @return
     */
    public Boolean deleteAppLogo(Integer id) throws SQLException;

    /**
     * 删除App信息
     * @param id
     * @return
     * @throws SQLException
     */
    public Boolean deleteAppInfo(Integer id) throws SQLException;

    /**
     * 上架与下架
     * @param appInfo
     * @return
     */
    public Boolean modify(AppInfo appInfo) throws Exception;
}
