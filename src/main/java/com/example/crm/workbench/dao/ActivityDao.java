package com.example.crm.workbench.dao;

import com.example.crm.workbench.domain.Activity;

import java.util.HashMap;
import java.util.List;

public interface ActivityDao {
    int saveActivity(Activity activity);

    List<Activity> getPageList(HashMap<String, Object> map);

    int getTotal(HashMap<String, Object> map);

    int deleteActivity(String[] ids);

    Activity getActivityById(String id);

    int updateActivity(Activity activity);

    Activity getDetail(String id);

    List<Activity> getActivityListByClueID(String id);

    List<Activity> getActivityListAndExcludeClueId(HashMap<String, String> map);

    List<Activity> getActivityByName(String searchText);
}
