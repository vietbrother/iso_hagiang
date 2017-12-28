package com.iso.dashboard.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;


public class AdminCfgUtil {

    Logger log = Logger.getLogger(AdminCfgUtil.class);
    private volatile static ObjectMapper objectMapper = null;
    private static AdminCfgUtil instance = null;
    
    private AdminCfgUtil() {
    }

    public static synchronized AdminCfgUtil getInstance() {
        if (instance == null) {
            instance = new AdminCfgUtil();
        }
        return instance;
    }


    public static void setInstance(AdminCfgUtil instance) {
        AdminCfgUtil.instance = instance;
    }

//    public String mapToJsonObj(Map map) {
//        String ret = null;
//        if (objectMapper == null) {
//            objectMapper = new ObjectMapper();
//        }
//        try {
//            ret = objectMapper.writeValueAsString(map);
//        } catch (JsonProcessingException ex) {
//            log.error(ex);
//            ret = Constants.EMPTY_CHARACTER;
//        }
//        return ret;
//    }

    public Map<String, Object> jsonStringToMap(String str) {
        Map<String, Object> ret = new HashMap<>();
        initObjectMapper();
        try {
            ret = objectMapper.readValue(str, HashMap.class);
        } catch (Exception ex) {
            log.error(ex);
        }
        return ret;
    }

    public synchronized void initObjectMapper() {
        if (objectMapper == null) {
            objectMapper = new ObjectMapper();
        }
    }


}
