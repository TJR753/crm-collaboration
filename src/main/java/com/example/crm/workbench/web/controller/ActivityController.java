package com.example.crm.workbench.web.controller;

import com.example.crm.settings.domain.User;
import com.example.crm.settings.service.UserService;
import com.example.crm.utils.DateTimeUtil;
import com.example.crm.utils.GetJson;
import com.example.crm.utils.UUIDUtil;
import com.example.crm.workbench.domain.Activity;
import com.example.crm.workbench.domain.ActivityRemark;
import com.example.crm.workbench.domain.vo.PageVo;
import com.example.crm.workbench.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

@Controller
public class ActivityController {
    @Autowired
    private UserService userService;
    @Autowired
    private ActivityService activityService;

    @ResponseBody
    @RequestMapping("/workbench/activity/getUserList.do")
    public String getUserList(){
        List<User> userList=userService.getUserList();
        String json = GetJson.getJson(userList);
        return json;
    }

    @ResponseBody
    @RequestMapping("/workbench/activity/saveActivity.do")
    public String saveActivity(Activity activity){
        String id= UUIDUtil.getUUID();
        activity.setId(id);
        String createTime= DateTimeUtil.getSysTime();
        activity.setCreateTime(createTime);
//        activity.setEditTime("");
//        activity.setEditBy("");
        //返回值，"success":true/false
        String json=activityService.saveActivity(activity);
        return json;
    }

    @ResponseBody
    @RequestMapping("/workbench/activity/getPageList.do")
    public String getPageList(HttpServletRequest request){
        String pageNOStr = request.getParameter("pageNO");
        int pageNO=Integer.parseInt(pageNOStr);
        String pageSizeStr = request.getParameter("pageSize");
        int pageSize=Integer.parseInt(pageSizeStr);

        String name = request.getParameter("name");
        String owner = request.getParameter("owner");
        String startTime = request.getParameter("startTime");
        String endTime = request.getParameter("endTime");
        //计算略过的条数
        int skipCount=pageSize*(pageNO-1);
        //封装map，传递
        HashMap<String,Object> map=new HashMap<>();
        map.put("name",name);
        map.put("owner",owner);
        map.put("startTime",startTime);
        map.put("endTime",endTime);
        map.put("skipCount",skipCount);
        map.put("pageSize",pageSize);
        PageVo<Activity> pageVO=activityService.getPageList(map);
        String json = GetJson.getJson(pageVO);
        return json;
    }

    @ResponseBody
    @RequestMapping("/workbench/activity/delete.do")
    public String deleteActivity(HttpServletRequest request){
        String[] ids = request.getParameterValues("id");
        boolean flag=activityService.deleteActivity(ids);
        String flag1 = GetJson.getFlag(flag);
        return flag1;
    }

    @ResponseBody
    @RequestMapping("/workbench/activity/getUserListAndActivity.do")
    public String getUserListAndActivity(HttpServletRequest request){
        String id = request.getParameter("id");
        Activity activity=activityService.getActivityById(id);
        List<User> userList = userService.getUserList();
        HashMap<String,Object> map=new HashMap<>();
        map.put("a",activity);
        map.put("userList",userList);
        String json = GetJson.getJson(map);
        return json;
    }

    @ResponseBody
    @RequestMapping("/workbench/activity/updateActivity.do")
    public String updateActivity(Activity activity){
        String editTime = DateTimeUtil.getSysTime();
        activity.setEditTime(editTime);
        boolean flag=activityService.updateActivity(activity);
        String flag1 = GetJson.getFlag(flag);
        return flag1;
    }

    @RequestMapping("/workbench/activity/getDetail.do")
    public void getDetail(String id, HttpServletRequest request, HttpServletResponse response){
        Activity activity=activityService.getDetail(id);
        request.setAttribute("a",activity);
        try {
            request.getRequestDispatcher("/workbench/activity/detail.jsp").forward(request,response);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @ResponseBody
    @RequestMapping("/workbench/activity/getActivityRemarkListById.do")
    public String getActivityRemarkListById(String id){
        List<ActivityRemark> arList=activityService.getActivityRemarkListById(id);
//        HashMap<String, Object> map = new HashMap<>();
//        map.put("arList",arList);
        String json = GetJson.getJson(arList);
        return json;
    }

    @ResponseBody
    @RequestMapping("/workbench/activity/deleteRemarkById.do")
    public String deleteRemarkById(String id){
        boolean flag=activityService.deleteRemarkById(id);
        String flag1 = GetJson.getFlag(flag);
        return flag1;
    }

    @ResponseBody
    @RequestMapping("/workbench/activity/saveRemark.do")
    public String saveRemark(ActivityRemark activityRemark){
        String uuid = UUIDUtil.getUUID();
        activityRemark.setId(uuid);
        activityRemark.setCreateTime(DateTimeUtil.getSysTime());
        activityRemark.setEditFlag("0");
        boolean flag=activityService.saveActivityRemark(activityRemark);
        HashMap<String, Object> map = new HashMap<>();
        map.put("success",flag);
        map.put("ar",activityRemark);
        String json = GetJson.getJson(map);
        System.out.println(activityRemark);
        return json;
    }

    @ResponseBody
    @RequestMapping("/workbench/activity/updateRemark.do")
    public String updateRemark(ActivityRemark activityRemark){
        activityRemark.setEditTime(DateTimeUtil.getSysTime());
        activityRemark.setEditFlag("1");
        boolean flag=activityService.updateRemark(activityRemark);
        HashMap<String, Object> map = new HashMap<>();
        map.put("success",flag);
        map.put("ar",activityRemark);
        String json = GetJson.getJson(map);
        System.out.println(activityRemark);
        return json;
    }
}
