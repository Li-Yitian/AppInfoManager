package cn.sp.appinfo.controller.backendUserController;

import cn.sp.appinfo.pojo.*;
import cn.sp.appinfo.service.appCategoryService.AppCategoryService;
import cn.sp.appinfo.service.appVersionService.AppVersionService;
import cn.sp.appinfo.service.appinfoService.impl.AppinfoServiceImpl;
import cn.sp.appinfo.service.dataDictionaryService.DataDictionaryService;
import cn.sp.appinfo.tool.Page;
import cn.sp.appinfo.tool.TypeCode;
import com.sun.xml.internal.bind.v2.schemagen.xmlschema.Appinfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import sun.rmi.log.LogInputStream;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.List;

@Controller
@RequestMapping("/manager/backend/app")
public class BackendUserAppInfoController {
    @Resource
    private AppinfoServiceImpl appinfoService; //app信息对象
    @Autowired
    private Page page; //分页对象
    @Resource
    private DataDictionaryService datas; //平台对象
    @Resource
    private AppCategoryService appCategory; //菜单类别对象
    @Resource
    private AppVersionService appVersionService;

    /**
     * 审核
     */
    @PostMapping("/checksave")
    public String checksave(Integer id,Integer status) throws SQLException {
        AppInfo appInfo = new AppInfo();
        appInfo.setId(id);
        appInfo.setStatus(status);
        appinfoService.updateAppInfo(appInfo);
        return "redirect:/manager/backend/app/list";
    }
    /**
     * 去审核页面
     */
    @RequestMapping("/check")
    public String doAppcheck(Integer aid,Integer vid,Model model){
        AppVersion appVersion = new AppVersion();//版本
        appVersion.setId(vid);
        AppInfo appInfo = appinfoService.getAppInfoById(aid);//App信息
        AppVersion newAppVersionByAppid = appVersionService.getNewAppVersionByAppid(appVersion);//该app最新的版本
        model.addAttribute("appInfo", appInfo);
        model.addAttribute("appVersion", newAppVersionByAppid);
        return "jsp/backend/appcheck";
    }

    /**
     * 显示app信息
     *
     * @param session
     * @param pageIndex
     * @return
     */
    @RequestMapping("/list")
    public String test(AppInfo appinfo, HttpSession session, Integer pageIndex,Model model) {
        Integer pageNo = pageIndex != null ? pageIndex : 1;
        List<DataDictionary> dataDictionarys = datas.getDataDictionarys(TypeCode.APP_FLATFORM);
        DevUser devUser = (DevUser) session.getAttribute("devUserSession");
        page.setPageNo(pageNo);
        appinfo.setStatus(1);
        appinfoService.getAppinfoByPage(appinfo, page);
        model.addAttribute("softwareName",appinfo.getSoftwareName());
        if (appinfo.getFlatformId() != null){
            model.addAttribute("flatformId",appinfo.getFlatformId());
        }
        //三级菜单
        if(appinfo.getCategoryLevel1() != null){
            model.addAttribute("categoryLevel1",appinfo.getCategoryLevel1());
            if(appinfo.getCategoryLevel2()!=null){
                model.addAttribute("categoryLevel2List", appCategory.getAppCategoryByparentId(appinfo.getCategoryLevel1()));
                model.addAttribute("categoryLevel2",appinfo.getCategoryLevel2());
                if(appinfo.getCategoryLevel3()!=null){
                    model.addAttribute("categoryLevel3List", appCategory.getAppCategoryByparentId(appinfo.getCategoryLevel2()));
                    model.addAttribute("categoryLevel3",appinfo.getCategoryLevel3());
                }
            }
        }
        model.addAttribute("pages", page);//分页
        model.addAttribute("flatFormList", dataDictionarys); //平台
        model.addAttribute("categoryLevel1List", appCategory.getAppCategoryByparentId(null));
        return "jsp/backend/applist";
    }

    /**
     * 菜单查询
     */
    @GetMapping("/categorylevellist.json")
    @ResponseBody//返回json格式
    public List<AppCategory> getAppCategoryByparentId(Integer pid) {
        List<AppCategory> list = appCategory.getAppCategoryByparentId(pid);
        return list;
    }


}
