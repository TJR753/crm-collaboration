package com.example.crm.workbench.dao;

import com.example.crm.workbench.domain.TranHistory;

import java.util.List;

public interface TranHistoryDao {

    int save(TranHistory tranHistory);

    List<TranHistory> getTranHistory(String tranId);
}
