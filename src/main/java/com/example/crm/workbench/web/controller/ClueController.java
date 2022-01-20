package com.example.crm.workbench.web.controller;

import com.example.crm.settings.domain.User;
import com.example.crm.utils.DateTimeUtil;
import com.example.crm.utils.GetJson;
import com.example.crm.utils.UUIDUtil;
import com.example.crm.workbench.domain.Activity;
import com.example.crm.workbench.domain.Clue;
import com.example.crm.workbench.domain.ClueActivityRelation;
import com.example.crm.workbench.domain.Tran;
import com.example.crm.workbench.service.ActivityService;
import com.example.crm.workbench.service.ClueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/workbench/clue")
public class ClueController {
    @Autowired
    private ClueService clueService;
    @Autowired
    private ActivityService activityService;

    @RequestMapping("/getUserList.do")
    public String getUserList(){
        List<User> userList=clueService.getUserList();
        String json = GetJson.getJson(userList);
        return json;
    }
    @RequestMapping("/saveClue.do")
    public String saveClue(Clue clue){
        clue.setId(UUIDUtil.getUUID());
        clue.setCreateTime(DateTimeUtil.getSysTime());
        boolean flag=clueService.saveClue(clue);
        String flag1 = GetJson.getFlag(flag);
        return flag1;
    }
    @RequestMapping("/getDetail.do")
    public void getDetail(String id, HttpServletRequest request, HttpServletResponse response){
        Clue clue=clueService.getDetail(id);
        request.setAttribute("clue",clue);
        try {
            request.getRequestDispatcher("/workbench/clue/detail.jsp").forward(request,response);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @RequestMapping("/getActivityList.do")
    public String getActivityListByClueID(String id){
        List<Activity> activityList=activityService.getActivityListByClueID(id);
        String json = GetJson.getJson(activityList);
        return json;
    }
    @RequestMapping("/disassociate.do")
    public String disassociate(String id){
        boolean flag=clueService.disassociate(id);
        String flag1 = GetJson.getFlag(flag);
        return flag1;
    }
    @RequestMapping("/getActivityListAndExcludeClueId.do")
    public String getActivityListAndExcludeClueId(@RequestParam("id") String id, @RequestParam("searchText") String searchText){
        HashMap<String, String> map = new HashMap<>();
        map.put("id",id);
        map.put("searchText",searchText);
        List<Activity> activityList=activityService.getActivityListAndExcludeClueId(map);
        String json = GetJson.getJson(activityList);
        return json;
    }
    @RequestMapping("/linkedActivity.do")
    public String linkedActivity(ClueActivityRelation clueActivityRelation){
        clueActivityRelation.setId(UUIDUtil.getUUID());
        boolean flag=clueService.getlinkedActivity(clueActivityRelation);
        String flag1 = GetJson.getFlag(flag);
        return flag1;
    }
    @RequestMapping("/getActivityByName.do")
    public String getActivityByName(String searchText){
        List<Activity> activityList=activityService.getActivityByName(searchText);
        String json = GetJson.getJson(activityList);
        return json;
    }
    @RequestMapping("/convertClue.do")
    public void convertClue(Tran tran, HttpServletRequest request,HttpServletResponse response){
        String flag = request.getParameter("flag");
        String clueId = request.getParameter("clueId");
        System.out.println(tran);
        User user = (User)request.getSession().getAttribute("user");
        boolean flag1=clueService.convertClue(tran,flag,clueId,user);
        if(flag1){
            try {
                request.getRequestDispatcher("/workbench/clue/index.jsp").forward(request,response);
            } catch (ServletException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    @RequestMapping("/save")
    public void save() {
        clueService.save();
    }
}
