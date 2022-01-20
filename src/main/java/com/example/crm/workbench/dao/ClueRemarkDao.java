package com.example.crm.workbench.dao;

import com.example.crm.workbench.domain.ClueRemark;

import java.util.List;

public interface ClueRemarkDao {

    List<ClueRemark> getClueReamrk(String clueId);

    int deleteByClueId(String clueId);

    void save(ClueRemark clueRemark);
}
