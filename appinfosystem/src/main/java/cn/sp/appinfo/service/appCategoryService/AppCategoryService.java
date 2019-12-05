package cn.sp.appinfo.service.appCategoryService;


import cn.sp.appinfo.pojo.AppCategory;

import java.util.List;

public interface AppCategoryService {
    public List<AppCategory> getAppCategoryByparentId(Integer parentId);
}
