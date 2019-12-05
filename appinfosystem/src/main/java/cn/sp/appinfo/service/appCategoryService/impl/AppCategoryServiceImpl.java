package cn.sp.appinfo.service.appCategoryService.impl;

import cn.sp.appinfo.dao.AppCategoryMapper;
import cn.sp.appinfo.pojo.AppCategory;
import cn.sp.appinfo.service.appCategoryService.AppCategoryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service("appCategoryService")
@Transactional
public class AppCategoryServiceImpl implements AppCategoryService {
    @Resource
private AppCategoryMapper appCategory;
    @Override
    public List<AppCategory> getAppCategoryByparentId(Integer parentId) {
        List<AppCategory> appCategoryByparentId = appCategory.getAppCategoryByparentId(parentId);
        return appCategoryByparentId;
    }
}
