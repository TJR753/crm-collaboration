package com.example.crm.workbench.service;

import com.example.crm.settings.domain.User;
import com.example.crm.workbench.domain.Tran;
import com.example.crm.workbench.domain.TranHistory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface TranService {
    List<User> getUseList();

    List<String> getCustomerName(String name);

    boolean save(Tran tran);

    Tran getTranById(String id);

    List<TranHistory> getTranHistory(String tranId);

    HashMap<String, Object> changeStage(Tran tran);

    Map<String, Object> getChart();
}
