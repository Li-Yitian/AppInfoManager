package cn.sp.appinfo.dao;

import cn.sp.appinfo.pojo.DataDictionary;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *数据字典
 */
@Repository
@Mapper
public interface DataDictionaryMapper {
    /**
     * 查询出所有平台
     */
    public List<DataDictionary> getDataDictionarys(String typeCode);

}
