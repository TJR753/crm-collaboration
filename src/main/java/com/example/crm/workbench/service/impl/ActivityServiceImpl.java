package com.example.crm.workbench.service.impl;

import com.example.crm.utils.DateTimeUtil;
import com.example.crm.utils.GetJson;
import com.example.crm.workbench.dao.ActivityDao;
import com.example.crm.workbench.dao.ActivityRemarkDao;
import com.example.crm.workbench.domain.Activity;
import com.example.crm.workbench.domain.ActivityRemark;
import com.example.crm.workbench.domain.vo.PageVo;
import com.example.crm.workbench.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class ActivityServiceImpl implements ActivityService {
    @Autowired
    private ActivityDao activityDao;
    @Autowired
    private ActivityRemarkDao activityRemarkDao;

    @Override
    public String saveActivity(Activity activity) {
        int i=activityDao.saveActivity(activity);
        String json=null;
        if(i==1){
            json = GetJson.getFlag(true);
        }else{
            json = GetJson.getFlag(false);
        }
        return json;
    }

    @Override
    public PageVo<Activity> getPageList(HashMap<String, Object> map) {
        //total number
        int total=activityDao.getTotal(map);
        //pageList
        List<Activity> pageList=activityDao.getPageList(map);
        PageVo<Activity> pageVo = new PageVo<>();
        pageVo.setTotal(total);
        pageVo.setPageList(pageList);
        return pageVo;
    }

    @Override
    public boolean deleteActivity(String[] ids) {
        //删除activity
        int count1=activityDao.deleteActivity(ids);

        //删除activityRemark
        int count2=activityRemarkDao.getActivityRemark(ids);
        int count3=activityRemarkDao.deleteActivityRemark(ids);
         if(count1==ids.length&&count2==count3) {
            return true;
         }
        return false;
    }

    @Override
    public Activity getActivityById(String id) {
        Activity activity=activityDao.getActivityById(id);
        String time = DateTimeUtil.getSysTime();
        activity.setEditTime(time);
        return activity;
    }

    @Override
    public boolean updateActivity(Activity activity) {
        int i=activityDao.updateActivity(activity);
        if(i==1){
            return true;
        }
        return false;
    }

    @Override
    public Activity getDetail(String id) {
        Activity activity=activityDao.getDetail(id);
        return activity;
    }

    @Override
    public List<ActivityRemark> getActivityRemarkListById(String id) {
        List<ActivityRemark> arList=activityRemarkDao.getActivityRemarkListById(id);
        return arList;
    }

    @Override
    public boolean deleteRemarkById(String id) {
        int i=activityRemarkDao.deleteRemarkById(id);
        if(i==1){
            return true;
        }
        return false;
    }

    @Override
    public boolean saveActivityRemark(ActivityRemark activityRemark) {
        int i=activityRemarkDao.saveActivityRemark(activityRemark);
        if(i==1){
            return true;
        }
        return false;
    }

    @Override
    public boolean updateRemark(ActivityRemark activityRemark) {
        int i=activityRemarkDao.updateRemark(activityRemark);
        if(i==1){
            return true;
        }
        return false;
    }

    @Override
    public List<Activity> getActivityListByClueID(String id) {
        List<Activity> activityList=activityDao.getActivityListByClueID(id);
        return activityList;
    }

    @Override
    public List<Activity> getActivityListAndExcludeClueId(HashMap<String, String> map) {
        List<Activity> activityList=activityDao.getActivityListAndExcludeClueId(map);
        return activityList;
    }

    @Override
    public List<Activity> getActivityByName(String searchText) {
        List<Activity> activityList=activityDao.getActivityByName(searchText);
        return activityList;
    }
}
