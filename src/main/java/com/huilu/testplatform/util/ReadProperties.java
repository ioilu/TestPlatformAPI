package com.huilu.testplatform.util;

import com.alibaba.fastjson.JSONObject;

import java.io.InputStream;
import java.util.Iterator;
import java.util.Properties;

public class ReadProperties {

    public JSONObject getProperties(String propertiesFile) {
        Properties properties = new Properties();
        JSONObject jsonObject = new JSONObject();
        try {
            InputStream in = Object.class.getResourceAsStream(propertiesFile);
            properties.load(in);
            Iterator<String> it = properties.stringPropertyNames().iterator();
            while (it.hasNext()) {
                String key = it.next();
                String value = properties.get(key).toString();
                jsonObject.put(key, value);
            }
            in.close();

        } catch (Exception e) {
        }
        return jsonObject;
    }
}