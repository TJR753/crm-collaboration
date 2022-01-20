package com.example.crm.workbench.web.controller;

import com.example.crm.settings.domain.User;
import com.example.crm.utils.DateTimeUtil;
import com.example.crm.utils.GetJson;
import com.example.crm.utils.UUIDUtil;
import com.example.crm.workbench.domain.Tran;
import com.example.crm.workbench.domain.TranHistory;
import com.example.crm.workbench.service.TranService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("/workbench/transaction")
@Controller
public class TranController {
    @Autowired
    private TranService tranService;

    @RequestMapping("/add.do")
    public ModelAndView add(ModelAndView mv){
        List<User> userList=tranService.getUseList();
        mv.addObject("userList",userList);
        mv.setViewName("/workbench/transaction/save");
        return mv;
    }

    @ResponseBody
    @RequestMapping("/getCustomerName.do")
    public String getCustomerName(String name){
        List<String> customerList=tranService.getCustomerName(name);
        String json = GetJson.getJson(customerList);
        return json;
    }

    @RequestMapping("/save.do")
    public ModelAndView save(Tran tran, ModelAndView mv){
        System.out.println(tran);
        //补充tran
        tran.setId(UUIDUtil.getUUID());
        tran.setCreateTime(DateTimeUtil.getSysTime());
        boolean flag=tranService.save(tran);
        //添加成功
        if(flag){
            mv.setViewName("/workbench/transaction/index");
        }
        return mv;
    }

    @RequestMapping("/getdetail.do")
    public ModelAndView getdetail(String id, ModelAndView mv, HttpServletRequest request){
        Tran tran=tranService.getTranById(id);
        Map<String,String> pmap = (Map<String, String>) request.getServletContext().getAttribute("pmap");
        String p = pmap.get(tran.getStage());
        tran.setPossibility(p);
        mv.addObject("tran",tran);
        mv.setViewName("/workbench/transaction/detail");
        return mv;
    }

    @ResponseBody
    @RequestMapping("/getTranHistory.do")
    public String getTranHistory(String tranId,HttpServletRequest request){
        List<TranHistory> tranHistoryList=tranService.getTranHistory(tranId);
        Map<String,String> pmap = (Map<String, String>) request.getServletContext().getAttribute("pmap");
        for(TranHistory tranHistory:tranHistoryList){
            String p = pmap.get(tranHistory.getStage());
            tranHistory.setPossibility(p);
        }
        String json = GetJson.getJson(tranHistoryList);
        return json;
    }
    @ResponseBody
    @RequestMapping("/changeStage.do")
    public String changeStage(Tran tran,HttpServletRequest request){
        System.out.println(tran);
        Map<String,String> pmap = (Map<String, String>) request.getServletContext().getAttribute("pmap");
        String possibility = pmap.get(tran.getStage());
        tran.setPossibility(possibility);
        HashMap<String, Object> map=tranService.changeStage(tran);
        String json = GetJson.getJson(map);
        return json;
    }
    @ResponseBody
    @RequestMapping("/getChart.do")
    public String getChart(){
        Map<String,Object> map=tranService.getChart();
        String json = GetJson.getJson(map);
        return json;
    }
}
