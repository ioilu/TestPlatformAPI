package com.huilu.testplatform.ztestframework;

import com.alibaba.fastjson.JSONObject;
import com.huilu.testplatform.entity.dao.TestCases;
import com.huilu.testplatform.util.ParamsUtil;
import okhttp3.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class OkHttpUtil {
//    private String url;
    private OkHttpClient okHttpClient;


    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    public OkHttpUtil() {
        okHttpClient = new OkHttpClient();
}

    public String doGet(String host, String api, String params) throws IOException {
        String url = host + api;
        String result = "";
        Request request;
        if (!"".equals(params) || null == params) {
            try {
                url = host + api + ParamsUtil.urlString(ParamsUtil.toMap(params));
            }catch (Exception e){
                e.printStackTrace();
                result = "参数格式不正确" + e.getMessage();
            }
        }
        System.out.println("url is :" + url);
        request = new Request.Builder()
                .url(url)
                .build();
        try {
            result = okHttpClient.newCall(request).execute().body().string();
        } catch (IOException e) {
            e.printStackTrace();
            result = e.getMessage();
        }
        return result;
    }

    public String doPost(String host,String api,Map<String,Object> params,String headers) throws IOException {
        String url = "";
        String result = "";
        Request request;
        RequestBody requestBody;
        url = host + api;
        JSONObject json = new JSONObject(params);
        //常规的参数请求
        if (headers == null || "".equals(headers)) {
            FormBody.Builder temp = new FormBody.Builder();
            for (String key : params.keySet()){
                temp.add(key, (String) params.get(key));
            }
            requestBody = temp.build();
            request = new Request.Builder().url(url).post(requestBody).build();
        }
        //requestbody请求
        else if (headers.contains("application/json")){
            requestBody = RequestBody.create(JSON, json.toJSONString());
            request = new Request.Builder().
                    addHeader("Content-Type", "application/json;charset=UTF-8")
                    .url(url)
                    .post(requestBody)
                    .build();
        }else {
            return "暂未处理的header类型";
        }

        try {
            result = okHttpClient.newCall(request).execute().body().string();
        } catch (IOException e) {
            e.printStackTrace();
            result = e.getMessage();
        }
        return result;
    }

    public String doHttpRequest(String host, TestCases testCases){
        String method = testCases.getMethod();
        OkHttpUtil okHttpUtil = new OkHttpUtil();
        String result;
        switch (method){
            case "GET": {
                try {
                    result = okHttpUtil.doGet(host, testCases.getApi(), testCases.getParams());
                } catch (IOException e) {
                    e.printStackTrace();
                    result = e.getMessage();
                };
                break;
            }
            case "POST": {
                Map<String, Object> postParamMap = null;
                try {
                    postParamMap = ParamsUtil.toMap(testCases.getParams());
                } catch (Exception e) {
                    result = e.getMessage();
                    e.printStackTrace();
                }
                try {
                    result = okHttpUtil.doPost(host, testCases.getApi(), postParamMap, testCases.getHeaders());
                } catch (IOException e) {
                    e.printStackTrace();
                    result = e.getMessage();
                } ;
                break;
            }
            default:
                result = "test_cases表的method类型暂不支持";
        }
        return result;
    }

    public static void main(String[] args) throws IOException {
        OkHttpUtil okHttpUtil = new OkHttpUtil();
        String s = okHttpUtil.doGet("http://localhost:8090","/user/getUser/1","");
        System.out.println( "=================="+s);
        OkHttpUtil okHttpUtil1 = new OkHttpUtil();

        Map<String,Object> param = new HashMap<String, Object>();
        param.put("id","1");
        param.put("name","okhttp");
        param.put("code","234234");
        String s1 = okHttpUtil1.doPost("http://localhost:8090","/service/update",param,"");
        System.out.println(s1);
    }
}
