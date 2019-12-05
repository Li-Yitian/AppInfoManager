package cn.sp.appinfo.service.appinfoService.impl;

import cn.sp.appinfo.dao.AppInfoMapper;
import cn.sp.appinfo.dao.AppVersionMapper;
import cn.sp.appinfo.pojo.AppInfo;
import cn.sp.appinfo.pojo.AppVersion;
import cn.sp.appinfo.service.appinfoService.AppinfoService;
import cn.sp.appinfo.tool.Page;
import com.sun.xml.internal.bind.v2.schemagen.xmlschema.Appinfo;
import org.apache.ibatis.transaction.Transaction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.File;
import java.sql.SQLException;
import java.util.List;

@Service
@Transactional
public class AppinfoServiceImpl implements AppinfoService {
    @Resource
    private AppInfoMapper appinfom;//app信息
    @Resource
    private AppVersionMapper appVersionMapper;//apk版本
    private  Transaction transaction;//事务管理
    /**
     *save状态
     */
    public Boolean saveModify(AppInfo appInfo,Integer status) throws SQLException {
        AppVersion appVersion = new AppVersion();
        appVersion.setId(appInfo.getVersionId());
        appVersion.setAppId(appInfo.getId());
        appVersion.setModifyBy(appInfo.getDevId());
        appVersion.setPublishStatus(status);
        if(appVersionMapper.updateAppVersion(appVersion)>0){
            return true;
        }else{
            transaction.rollback();
            return false;
        }


    }
    /**
     * 上架与下架
     */
    public Boolean modify(AppInfo appInfo)throws Exception{
        AppInfo app = appinfom.getAppInfoById(appInfo.getId());
        System.out.println("==="+app);
        if(app.getModifyBy()<0){
            System.out.println("进了123");
            throw new Exception();
        }
        System.out.println("=============进了");
        if(app!=null){
            /**
             * 状态为2 or 5 则上架
             * 为 4 则下架 版本依然为发布状态
             */
            switch (app.getStatus()){
                case 2:
                case 5:
                    System.out.println("=============进了2"); appInfo.setStatus(4);
                    if(appinfom.updateAppInfo(appInfo) < 0)
                        throw  new Exception();
                    if(saveModify(app,2)==false){
                        System.out.println("=============异常最后3");
                        throw  new Exception();
                    }
                    break;
                case 4:
                    System.out.println("=============进了3"); appInfo.setStatus(5);
                    if(appinfom.updateAppInfo(appInfo) < 0){
                        System.out.println("=============异常最后2");
                        throw  new Exception();
                    }
                    break;
            }
        }else{
            System.out.println("=============异常最后");
            throw  new Exception();
        } System.out.println("=============没了");
        return true;
    }
    /**
     * 分页显示所有appinfo
     */
    @Override
    public void getAppinfoByPage(AppInfo appinfo, Page page) {
        page.setRows(appinfom.getAppinfoRows(appinfo));
        List<Appinfo> list = appinfom.getAppInfoByPage(appinfo, page.getFirst(), page.getPageSize());
        page.setList(list);
    }
    /**
     * 新增App信息
     */
    @Override
    public Boolean addAppInfo(AppInfo appInfo) {
        Integer row = appinfom.addAppInfo(appInfo);
        if (row > 0) {
            return true;
        }
        return false;
    }

    /**
     * 查询apk是否存在
     *
     * @param apkName
     * @return
     */
    @Override
    public Integer apkexist(String apkName) {
        Integer row = appinfom.getAPKNameByApkName(apkName);
        return row;
    }

    /**
     * 读取单个app信息
     *
     * @param id
     * @return
     */
    @Override
    public AppInfo getAppInfoById(Integer id) {
        AppInfo appInfoById = appinfom.getAppInfoById(id);
        return appInfoById;
    }

    /**
     * 更新app信息
     *
     * @param appInfo
     * @return
     */
    @Override
    public Boolean updateAppInfo(AppInfo appInfo) throws SQLException {
        Integer row = appinfom.updateAppInfo(appInfo);
        if (row > 0) {
            return true;
        }else{
            transaction.rollback();
            return false;
        }

    }

    /**
     * 删除app图片
     *
     * @param id
     * @return
     */
    @Override
    public Boolean deleteAppLogo(Integer id) throws SQLException {
        Integer row = appinfom.deleteAppLogo(id);
        if (row > 0) {
            return true;
        }else{
            transaction.rollback();
            return false;
        }

    }

    /**
     * 删除 APPInfo信息
     *
     * @param id
     * @return
     */
    @Override
    public Boolean deleteAppInfo(Integer id) throws SQLException {
        AppInfo appInfo = appinfom.getAppInfoById(id);
        System.out.println(appInfo + "==========" + appinfom.getAppInfoById(id) + "----" + id);
        System.out.println(appVersionMapper.getAppVersionByid(appInfo.getId()) + "=====\n" + appInfo);
        List<AppVersion> appVersion = appVersionMapper.getAppVersionByid(appInfo.getId());//获得需要删除的APK版本路径
        File filelog = new File(appInfo.getLogoLocPath());//需要删除的图片路径

        //删除物理文件
        filelog.delete();
        for (AppVersion version : appVersion) {
            File fileapk = new File(version.getApkLocPath());
            fileapk.delete();
        }
        Integer row = appinfom.deleteAppInfo(id);//删除app信息
        if (row > 0) {
            //删除apk版本数据，失败回滚
            if (appVersionMapper.deleteAppVersionByappid(id) > 0) {
                return true;
            } else {
                 transaction.rollback();
            }
        }else {
            transaction.rollback();
        }
        return false;
    }
}
