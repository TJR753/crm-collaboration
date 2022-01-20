package com.example.crm.settings.service.serviceImpl;

import com.example.crm.settings.dao.DicTypeDao;
import com.example.crm.settings.dao.DicValueDao;
import com.example.crm.settings.domain.DicValue;
import com.example.crm.settings.service.DicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DicServiceImpl implements DicService {
    @Autowired
    private DicTypeDao dicTypeDao;
    @Autowired
    private DicValueDao dicValueDao;

    @Override
    public Map<String, List<DicValue>> getAll() {
        List<String> typeList=dicTypeDao.getType();
        HashMap<String, List<DicValue>> map = new HashMap<>();
        for(String type:typeList){
            List<DicValue> valueList=dicValueDao.getValues(type);
            map.put(type,valueList);
        }
        return map;
    }
}
