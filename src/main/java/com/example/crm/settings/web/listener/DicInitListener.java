package com.example.crm.settings.web.listener;

import com.example.crm.settings.domain.DicValue;
import com.example.crm.settings.service.DicService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.*;

@WebListener
public class DicInitListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        //缓存数据字典
        ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
        DicService dicService = (DicService)ac.getBean("dicServiceImpl");
        System.out.println("初始化上下文作用域对象");
        ServletContext servletContext = sce.getServletContext();
        //map(code,List<DicValue>)
        Map<String, List<DicValue>> map=dicService.getAll();
        Set<String> keySet = map.keySet();
        for(String key:keySet){
            servletContext.setAttribute(key,map.get(key));
        }
        //建立阶段和可能性的键值对
        ResourceBundle bundle = ResourceBundle.getBundle("stagePossbility");
        Enumeration<String> keys = bundle.getKeys();
        HashMap<String, String> pmap = new HashMap<>();
        while(keys.hasMoreElements()){
            String key = keys.nextElement();
            String value = bundle.getString(key);
            pmap.put(key,value);
        }
        servletContext.setAttribute("pmap",pmap);
    }
}
