package com.example.crm.workbench.dao;

import com.example.crm.workbench.domain.Tran;

import java.util.List;
import java.util.Map;

public interface TranDao {

    int save(Tran tran);

    Tran getTranById(String id);

    int update(Tran t);

    int getTotal();

    List<Map<String, Object>> getStageGroup();
}
