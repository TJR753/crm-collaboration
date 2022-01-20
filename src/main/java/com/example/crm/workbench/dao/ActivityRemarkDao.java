package com.example.crm.workbench.dao;

import com.example.crm.workbench.domain.ActivityRemark;

import java.util.List;

public interface ActivityRemarkDao {
    int getActivityRemark(String[] ids);

    int deleteActivityRemark(String[] ids);

    List<ActivityRemark> getActivityRemarkListById(String id);

    int deleteRemarkById(String id);

    int saveActivityRemark(ActivityRemark activityRemark);

    int updateRemark(ActivityRemark activityRemark);
}
