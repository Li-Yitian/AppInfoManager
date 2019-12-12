package cn.sp.appinfo.controller.devUserController;

import cn.sp.appinfo.pojo.*;
import cn.sp.appinfo.service.appCategoryService.AppCategoryService;
import cn.sp.appinfo.service.appVersionService.AppVersionService;
import cn.sp.appinfo.service.appinfoService.impl.AppinfoServiceImpl;
import cn.sp.appinfo.service.dataDictionaryService.DataDictionaryService;
import cn.sp.appinfo.tool.Page;
import cn.sp.appinfo.tool.TypeCode;
import cn.sp.appinfo.tool.UUIDTool;
import com.sun.xml.internal.ws.resources.HttpserverMessages;
import org.apache.commons.io.FilenameUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@Controller
@RequestMapping("/dev/flatform/app")
public class DevUserAppinfoController {
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
    private final  Integer FILESIZE =50000000;
    private final  Integer IMAGESSIZE =100000;
    /**
     * 上架与下架
     */
    @RequestMapping("/{appId}/sale.json")
    @ResponseBody
    public Map<String,Object> changeStatus(@PathVariable("appId") Integer appId,Model model,HttpSession session) throws Exception {
        Map<String,Object> map = new HashMap<>();
        DevUser devUser = (DevUser) session.getAttribute("devUserSession");
        map.put("errorCode","0");
        if(devUser==null){
            map.put("errorCode","param000001");
            return map;
        }
        AppInfo appInfo = new AppInfo();
        appInfo.setId(appId);
        appInfo.setModifyBy(devUser.getId());
        try {
            if(appinfoService.modify(appInfo)){
                map.put("resultMsg","success");
            }else {
                map.put("resultMsg","failed");
            }
        }
        catch (Exception e){
            map.put("errorCode","exception000001");
        }
        return map;
    }
    /**
     * 修改版本信息
     */
    @RequestMapping("/appversionmodifysave")
    public String appversionmodify(AppVersion appVersion, MultipartFile attach, HttpServletRequest request, HttpSession session,Model model) throws IOException {
       if(attach.getSize()>FILESIZE){
           model.addAttribute("fileUploadError","文件不得超过500M!");
           return "jsp/developer/appversionmodify";
       }else{
           if(FilenameUtils.getExtension(attach.getOriginalFilename()).equalsIgnoreCase("apk")){
               if (!attach.isEmpty()) {
                   String relatively = "/statics/uploadfiles/" + attach.getOriginalFilename();
                   String rootPath = request.getServletContext().getRealPath("/");//返回服务器根目录
                   String completePath = rootPath + relatively;//完整路径
                   appVersion.setApkFileName(attach.getOriginalFilename());//apk文件名
                   appVersion.setDownloadLink(relatively);//database下载路径
                   appVersion.setApkLocPath(completePath);//database完整路径
                   attach.transferTo(new File(completePath));
               }else{
                   model.addAttribute("fileUploadError","上传的文件不能为空!");
                   return "jsp/developer/appversionmodify";
               }
           }else{
               model.addAttribute("fileUploadError","上传的文件格式不正确!");
               return "jsp/developer/appversionmodify";
           }

       }

        DevUser devUser = (DevUser) session.getAttribute("devUserSession");
        appVersion.setCreatedBy(devUser.getId());
        //更新版本
        appVersionService.updateAppVersion(appVersion);
        return "redirect:/dev/flatform/app/list";
    }

    /**
     * 去修改页面
     */
    @RequestMapping("/appversionmodify")
    public String doAppversionmodify(@RequestParam("vid") Integer vid, @RequestParam("aid") Integer aid, Model model) {
        AppVersion appVersion = new AppVersion();
        appVersion.setAppId(aid);
        List<AppVersion> appVersionList = appVersionService.getAppVersionListByid(aid);//该app的所有版本
        AppVersion newAppVersionByAppid = appVersionService.getNewAppVersionByAppid(appVersion);//该app最新的版本
        model.addAttribute("appVersionList", appVersionList);
        model.addAttribute("appVersion", newAppVersionByAppid);
        return "jsp/developer/appversionmodify";
    }

    /**
     * 添加新的APP版本信息
     */
    @RequestMapping("/addversionsave")
    public String appversionAdd(AppVersion appVersion, @RequestParam("a_downloadLink") MultipartFile a_downloadLink, Model model,HttpSession session, HttpServletRequest request) throws IOException {
        if(a_downloadLink.getSize()>FILESIZE){
            model.addAttribute("fileUploadError","文件不得超过500M!");
            return "jsp/developer/appversionmodify";
        }else {
            if (FilenameUtils.getExtension(a_downloadLink.getOriginalFilename()).equalsIgnoreCase("apk")) {
                //判断用户是否提交了文件
                if (!a_downloadLink.isEmpty()) {
                String rootPath = request.getServletContext().getRealPath("/");//返回服务器根目录
                String relatively = "/statics/uploadfiles/" + a_downloadLink.getOriginalFilename();//相对路径
                String completePath = rootPath + relatively;//完整路径
                appVersion.setApkFileName(a_downloadLink.getOriginalFilename());//apk文件名
                appVersion.setDownloadLink(relatively);//database下载路径
                appVersion.setApkLocPath(completePath);//database完整路径
                a_downloadLink.transferTo(new File(completePath));
                }else{
                model.addAttribute("fileUploadError","上传的文件不能为空!");
                return "jsp/developer/appversionmodify";
            }
            }else {
                model.addAttribute("fileUploadError","上传的文件格式不正确!");
                return "jsp/developer/appversionmodify";
            }
        }
        DevUser devUser = (DevUser) session.getAttribute("devUserSession");
        appVersion.setCreatedBy(devUser.getId());
        appVersionService.addAppVersion(appVersion);
        return "redirect:/dev/flatform/app/list";
    }

    /**
     * 去添加APP信息APK页面
     *
     * @param id
     * @param model
     * @return
     */
    @RequestMapping("/appversionadd")
    public String doappversionadd(Integer id, Model model) {

        List<AppVersion> appVersionList = appVersionService.getAppVersionListByid(id);//版本信息
        AppVersion appVersion = new AppVersion();
        appVersion.setAppId(id);//id作用是更新app的最新版本信息

        model.addAttribute("appVersionList", appVersionList);
        model.addAttribute("appVersion", appVersion);
        return "jsp/developer/appversionadd";
    }

    /**
     * 删除选中的APP信息
     */
    @RequestMapping("delapp.json")
    @ResponseBody
    public Map<String, Object> deleteAppInfo(Integer id, Model model) throws SQLException {
        Map<String, Object> map = new HashMap<>();
        //判断是否存在该条信息
        if (appinfoService.getAppInfoById(id) != null) {
            //删除该信息，返回是否成功
            Boolean judge = appinfoService.deleteAppInfo(id);
            if (judge) {
                map.put("delResult", "true");
            } else {
                map.put("delResult", "false");
            }
        } else {
            map.put("delResult", "notexist");
        }
        return map;
    }

    /**
     * 查看App信息
     */
    @RequestMapping("/appview/{id}")
    public String examine(@PathVariable("id") Integer id, Model model, HttpSession session) {

        AppInfo appInfo = appinfoService.getAppInfoById(id);//App信息
        List<AppVersion> appVersion = appVersionService.getAppVersionListByid(id);//版本信息
        model.addAttribute("appInfo", appInfo);
        model.addAttribute("appVersionList", appVersion);
        return "jsp/developer/appinfoview";
    }

    /**
     * 保存更新APP信息
     */
    @RequestMapping("/appinfomodifysave")
    public String updateAppInfo(AppInfo appInfo, HttpSession session, @RequestParam("attach") MultipartFile attach, Model model, HttpServletRequest req) throws IllegalStateException, IOException, SQLException {
        //获得上传文件后缀
        String extension = FilenameUtils.getExtension(attach.getOriginalFilename());
        if(extension.equalsIgnoreCase("jpg") || extension.equalsIgnoreCase("png")
                ||extension.equalsIgnoreCase("jpg") || extension.equalsIgnoreCase("png")||attach.isEmpty()) {//上传图片格式
            //拼接新的文件名，判断上传的文件非空
            if (!attach.isEmpty()) {
                String newFileName = UUIDTool.getUUID() + "." + extension;
                //获得网站运行的根目录
                String rootPath = req.getServletContext().getRealPath("/");
                String relativePath = "/statics/images/" + newFileName; //相对路径，用于保存到数据库，以后从数据库读取出来，用图片显示在页面上
                String fullPath = rootPath + relativePath; //完整路径
                appInfo.setLogoPicPath(relativePath);//显示的图片路径
                appInfo.setLogoLocPath(fullPath);//服务器图片完整路径
                //查询session的登录id
                DevUser devUser = (DevUser) session.getAttribute("devUserSession");
                appInfo.setDevId(devUser.getId());
                //从数据库中查询出图片路径，用于前台页面显示图片
                //保存文件
                attach.transferTo(new File(fullPath));
            } else {
                appInfo.setLogoPicPath(null);//显示的图片路径
                appInfo.setLogoLocPath(null);//服务器图片完整路径
            }
            //更新APP信息
            appinfoService.updateAppInfo(appInfo);
        }else{
            appInfo = appinfoService.getAppInfoById(appInfo.getId());
            model.addAttribute("appInfo", appInfo);
            model.addAttribute("fileUploadError","上传的图片格式不正确!");
            return "jsp/developer/appinfomodify";
        }

        return "redirect:/dev/flatform/app/list";
    }

    /**
     * 删除图片或APK文件（flag判断）
     */
    @RequestMapping("delfile.json")
    @ResponseBody
    public Map<String, String> deleteImage(Integer id, String flag, Model model) throws SQLException {
        Map<String, String> map = new HashMap<>();
        //判断是否可以删除文件
        if (flag == null || flag == "" || id == null) {
            map.put("result", "failed");
        } else {
            if (flag.equals("logo")) {
                //读取服务器的完全路径
                File file = new File(appinfoService.getAppInfoById(id).getLogoLocPath());
                if (file.isFile()) {
                    //删除成功后改变数据库的图片路径为null
                    if (file.delete()) {
                        if (appinfoService.deleteAppLogo(id)) {
                            //更新路径
                            map.put("result", "success");
                        } else {
                            map.put("result", "failed");
                        }
                    } else {
                        map.put("result", "failed");
                    }
                }
            } else if (flag.equals("apk")) {
                AppVersion appVersion = new AppVersion();
                appVersion.setId(id);
                File file = new File(appVersionService.getNewAppVersionByAppid(appVersion).getApkLocPath());
                if (file.isFile()) {
                    if (file.delete()) {
                        if (appVersionService.deleteFilePathForNullById(id)) {
                            map.put("result", "success");
                        } else {
                            map.put("result", "failed");
                        }
                    } else {
                        map.put("result", "failed");
                    }
                }
            }
        }
        return map;
    }

    /**
     * 去更新页面
     */
    @RequestMapping("/appinfomodify")
    public String toUpdate(Integer id, Model model) {
        AppInfo appinfo = appinfoService.getAppInfoById(id);
        model.addAttribute("appInfo", appinfo);
        return "jsp/developer/appinfomodify";
    }

    /**
     * 新增app信息
     */
    @PostMapping("/appinfoaddsave")
    public String appinfoaddsave(AppInfo appInfo, HttpSession session, MultipartFile a_logoPicPath, Model model, HttpServletRequest req) throws IllegalStateException, IOException {
        //获得上传文件后缀
        String extension = FilenameUtils.getExtension(a_logoPicPath.getOriginalFilename());
        //拼接新的文件名
        String newFileName = UUIDTool.getUUID() + "." + extension;
        //获得网站运行的根目录
        String rootPath = req.getServletContext().getRealPath("/");
        //相对路径，用于保存到数据库，以后从数据库读取出来，用图片显示在页面上
        String relativePath = "/statics/images/" + newFileName;
        appInfo.setLogoPicPath(relativePath);//图片路径
        //完整路径
        String fullPath = rootPath + relativePath;
        appInfo.setLogoLocPath(fullPath);//服务器完整路径
        //查询session的登录id
        DevUser devUser = (DevUser) session.getAttribute("devUserSession");
        appInfo.setDevId(devUser.getId());
        //从数据库中查询出图片路径，用于前台页面显示图片
        model.addAttribute("imgPath", appInfo.getLogoPicPath());
        //保存文件
        a_logoPicPath.transferTo(new File(fullPath));
        //uploadFile.transferTo(new File("f:/"+newFileName));
        //新增信息
        appinfoService.addAppInfo(appInfo);
        return "redirect:/dev/flatform/app/list";
    }


    /**
     * 去新增页面
     */
    @RequestMapping("/appinfoadd")
    public String toAdd(Model model) {
        List<DataDictionary> dataDictionarys = datas.getDataDictionarys(TypeCode.APP_FLATFORM);
        model.addAttribute("flatformId", dataDictionarys);
        return "jsp/developer/appinfoadd";
    }

    /**
     * 显示app信息
     */
    @RequestMapping("/list")
    public String test(AppInfo appinfo, HttpSession session, Integer pageIndex, Model model) {
        //查询session的登录id
        DevUser devUser = session.getAttribute("devUserSession") != null ? (DevUser) session.getAttribute("devUserSession") : null;
        if (devUser != null) {
            appinfo.setDevId(devUser.getId());
        }
        //分页判断与查询
        Integer pageNo = pageIndex != null ? pageIndex : page.getPageNo();
        page.setPageNo(pageNo);
        appinfoService.getAppinfoByPage(appinfo, page);
        //判断传来的所属平台是否为空
        if (appinfo.getFlatformId() != null) {
            model.addAttribute("flatformId", appinfo.getFlatformId());
        }
        //三级菜单
        if (appinfo.getCategoryLevel1() != null) {
            model.addAttribute("categoryLevel1", appinfo.getCategoryLevel1());
            if (appinfo.getCategoryLevel2() != null) {
                model.addAttribute("categoryLevel2List", appCategory.getAppCategoryByparentId(appinfo.getCategoryLevel1()));
                model.addAttribute("categoryLevel2", appinfo.getCategoryLevel2());
                if (appinfo.getCategoryLevel3() != null) {
                    model.addAttribute("categoryLevel3List", appCategory.getAppCategoryByparentId(appinfo.getCategoryLevel2()));
                    model.addAttribute("categoryLevel3", appinfo.getCategoryLevel3());
                }
            }
        }

        //APP的平台与状态查询
        List<DataDictionary> dataDictionarys1 = datas.getDataDictionarys(TypeCode.APP_FLATFORM);
        List<DataDictionary> dataDictionarys2 = datas.getDataDictionarys(TypeCode.APP_STATUS);

        //传出数据
        model.addAttribute("queryStatus", appinfo.getStatus());
        model.addAttribute("softwareName", appinfo.getSoftwareName());
        model.addAttribute("pages", page);//分页
        model.addAttribute("flatFormList", dataDictionarys1); //  平台
        model.addAttribute("statusList", dataDictionarys2); // app状态
        model.addAttribute("categoryLevel1List", appCategory.getAppCategoryByparentId(null));
        return "jsp/developer/appinfolist";
    }

    /**
     * 菜单查询异步
     */
    @GetMapping("/categorylevellist.json")
    @ResponseBody//返回json格式
    public List<AppCategory> getDataDictionarys(Integer pid) {
        List<AppCategory> list = appCategory.getAppCategoryByparentId(pid);
        return list;
    }

    /**
     * 平台Json，异步查询
     */
    @RequestMapping("/datadictionarylist.json")
    @ResponseBody
    public List<DataDictionary> getDataDictionarys() {
        List<DataDictionary> dataDictionarys = datas.getDataDictionarys("APP_FLATFORM");
        return dataDictionarys;
    }

    /**
     * 查询APKName是否存在异步
     */
    @RequestMapping("/apkexist.json")
    @ResponseBody
    public Map<String, Object> apkexist(String APKName) {
        Map<String, Object> map = new HashMap<>();
        if (appinfoService.apkexist(APKName) <=0) {
            map.put("APKName", "noexist");
        } else if (APKName == null) {
            map.put("APKName", "empty");
        } else if (appinfoService.apkexist(APKName) > 0) {
            map.put("APKName", "exist");
        }
        return map;
    }
}
