package cn.sp.appinfo.dao;

import cn.sp.appinfo.pojo.AppCategory;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 菜单分类
 */
@Repository
@Mapper
public interface AppCategoryMapper {

    public List<AppCategory> getAppCategoryByparentId(Integer parentId);
}
