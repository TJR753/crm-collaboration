package com.example.crm.settings.web.controller;

import com.example.crm.exception.LoginException;
import com.example.crm.settings.domain.User;
import com.example.crm.settings.domain.vo.ResultData;
import com.example.crm.settings.service.UserService;
import com.example.crm.utils.MD5Util;
import com.example.crm.utils.PrintJson;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import springfox.documentation.spring.web.json.Json;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

@Api
@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @ApiOperation(value = "登录方法", notes = "返回值类型为json，{'success':true/false;'msg':错误信息}")
    @PostMapping(path = "/settings/user/login.do")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "loginAct",value = "账号",dataType = "string",paramType = "formData"),
            @ApiImplicitParam(name = "loginPwd",value = "密码",dataType = "string",paramType = "formData")
    })
    @ApiResponses({
            @ApiResponse(code=200,message = "登陆成功",response = ResultData.class)
    })
    @ResponseBody
    public String login(HttpServletRequest request, HttpServletResponse response){
        String loginAct = request.getParameter("loginAct");
        String loginPwd = request.getParameter("loginPwd");
        //账号密码加密
        loginPwd = MD5Util.getMD5(loginPwd);
        //map值:{"success":true/false,"msg":"错误信息"}
        String ip = request.getRemoteAddr();
        System.out.println(ip);
        ResultData resultData = new ResultData();
        try {
            User user = userService.login(loginAct, loginPwd, ip);
            //代码执行到这里说明没有抛出任何异常
            request.getSession().setAttribute("user",user);
            return  PrintJson.printJsonObj(resultData.setCode(200).setSuccess(true));
        } catch (LoginException e) {
            e.printStackTrace();
            //到这里说明抛出了异常
            String msg = e.getMessage();
            return  PrintJson.printJsonObj(resultData.setCode(200).setSuccess(true).setMsg("登陆失败"));
        }
    }
}
