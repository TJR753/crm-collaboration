package com.example.crm.workbench.service;

import com.example.crm.settings.domain.User;
import com.example.crm.workbench.domain.Clue;
import com.example.crm.workbench.domain.ClueActivityRelation;
import com.example.crm.workbench.domain.Tran;

import java.util.List;

public interface ClueService {
    List<User> getUserList();

    boolean saveClue(Clue clue);

    Clue getDetail(String id);

    boolean disassociate(String id);

    boolean getlinkedActivity(ClueActivityRelation clueActivityRelation);

    boolean convertClue(Tran tran, String flag, String clueId,User user);

    void save();
}
