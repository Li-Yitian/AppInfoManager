package cn.sp.appinfo.service.dataDictionaryService.impl;

import cn.sp.appinfo.dao.DataDictionaryMapper;
import cn.sp.appinfo.pojo.DataDictionary;
import cn.sp.appinfo.service.dataDictionaryService.DataDictionaryService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class DataDictionaryServiceImpl implements DataDictionaryService {
@Resource
private DataDictionaryMapper datam;
    @Override
    public List<DataDictionary> getDataDictionarys(String typeCode) {
        List<DataDictionary> dataDictionarys = datam.getDataDictionarys(typeCode);
        return dataDictionarys;
    }
}
