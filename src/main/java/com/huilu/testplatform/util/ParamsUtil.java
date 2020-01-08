package com.huilu.testplatform.util;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParamsUtil {
    public static String urlString(HashMap<String, Object> paramMap) {
        String result = "?";
        for(String key : paramMap.keySet()){
            result = result + key + "=" + paramMap.get(key)+ "&";
        }
        result = result.substring(0,result.length()-1);
        return result;
    }

    public static Map<String, Object> toMap(String paramsString) {
        Map<String, Object> map = new HashMap<String, Object>();
        if (paramsString != null || paramsString != "") {
//            System.out.println("paramString is:" + paramsString);
            String[] temp = paramsString.split("\\|");
            for (int i = 0; i < temp.length; i++) {
                String[] index = temp[i].split(":");
                map.put(index[0], index[1]);
            }
        }
        return map;
    }

    public static String[] toArray(String sqlString) {
        List<String> sqlList = new ArrayList<>();
        if (sqlString != null || sqlString != "") {
            return sqlString.split("\\|");
        }
        return null;
    }

    public static void main(String[] args) {
        String[] strings = toArray("123123;|fdsfs;|sdfs");
        for (String i : strings){
            System.out.println(i);
        }
    }
}
