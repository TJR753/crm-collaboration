package com.example.crm.workbench.service;

import com.example.crm.workbench.domain.Activity;
import com.example.crm.workbench.domain.ActivityRemark;
import com.example.crm.workbench.domain.vo.PageVo;

import java.util.HashMap;
import java.util.List;

public interface ActivityService {

    String saveActivity(Activity activity);

    PageVo<Activity> getPageList(HashMap<String, Object> map);

    boolean deleteActivity(String[] ids);

    Activity getActivityById(String id);

    boolean updateActivity(Activity activity);

    Activity getDetail(String id);

    List<ActivityRemark> getActivityRemarkListById(String id);

    boolean deleteRemarkById(String id);

    boolean saveActivityRemark(ActivityRemark activityRemark);

    boolean updateRemark(ActivityRemark activityRemark);

    List<Activity> getActivityListByClueID(String id);

    List<Activity> getActivityListAndExcludeClueId(HashMap<String, String> map);

    List<Activity> getActivityByName(String searchText);
}
