package com.example.crm.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;

public class GetJson {
    public static String getJson(Object obj){
        ObjectMapper om=new ObjectMapper();
        String json=null;
        try {
             json= om.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return json;
    }
    public static String getFlag(Boolean flag){
        HashMap<String, Boolean> map = new HashMap<>();
        map.put("success",flag);
        ObjectMapper om = new ObjectMapper();
        String json=null;
        try {
            json = om.writeValueAsString(map);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return json;
    }
}
