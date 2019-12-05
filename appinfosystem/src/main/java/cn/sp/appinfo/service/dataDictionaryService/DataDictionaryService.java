package cn.sp.appinfo.service.dataDictionaryService;

import cn.sp.appinfo.pojo.DataDictionary;

import java.util.List;

public interface DataDictionaryService {
    /**
     * 查询出所有平台
     */
    public List<DataDictionary> getDataDictionarys(String typeCode);
}
