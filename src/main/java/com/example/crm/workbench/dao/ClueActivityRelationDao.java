package com.example.crm.workbench.dao;


import com.example.crm.workbench.domain.ClueActivityRelation;

import java.util.List;

public interface ClueActivityRelationDao {


    int disassociate(String id);

    int linkedActivity(ClueActivityRelation clueActivityRelation);

    List<ClueActivityRelation> getLinkedActivity(String clueId);

    int deleteByClueId(String clueId);
}
