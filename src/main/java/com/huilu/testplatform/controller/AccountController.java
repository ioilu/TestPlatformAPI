package com.huilu.testplatform.controller;

import com.huilu.testplatform.config.Config;
import com.huilu.testplatform.entity.dao.AccountInfo;
import com.huilu.testplatform.entity.service.PlannedCasesForExecute;
import com.huilu.testplatform.mapper.JobMapper;
import com.huilu.testplatform.mapper.ServiceManagementMapper;
import com.huilu.testplatform.service.AccountService;
import com.huilu.testplatform.service.GetAllInfoForJob;
import com.huilu.testplatform.util.JsonResponse;
import com.huilu.testplatform.ztestframework.OkHttpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author:0xOO
 * @Date: 2018/9/26 0026
 * @Time: 14:42
 */

@RestController
@RequestMapping("/user")
public class AccountController {

    @Autowired
    AccountService accountService;

    @Autowired
    ServiceManagementMapper serviceManagementMapper;

    @Autowired
    GetAllInfoForJob getAllInfoForJob;

    @Autowired
    JobMapper jobMapper;

    @RequestMapping("/getUser/{id}")
    public AccountInfo GetUser(HttpServletRequest request,@PathVariable int id){
        HttpSession httpSession = request.getSession();
        System.out.println(httpSession);
        return accountService.getInfo(id);
    }

    @RequestMapping("/hello")
    public String hello(){
//        @RequestBody String s    //作为参数验证请求body
//        return JSON.parseObject(s).getString("password");
        return "hello world!";
    }


    @RequestMapping(value = "/addUser", method = {RequestMethod.POST} )
    public String addUser(@NotNull AccountInfo accountInfo){

//        AccountInfo accountInfo = new AccountInfo();
//        accountInfo = JSON.parseObject(jsonObject,AccountInfo.class);

        if (!accountService.checkParams(accountInfo)) {
            return "{\"msg\":\"参数不合法\",\"status\":\"ok\"}";
        }
        if(!accountService.checkAccountNotExist(accountInfo)) {
            return "{\"msg\":\"该用户已存在\",\"status\":\"ok\"}";
        }
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        accountInfo.setCreatetime(df.format(new Date()));
        accountInfo.setId(null);
        int i;
        try {
            i =accountService.addUser(accountInfo);
        }catch (RuntimeException e){
            return "{\"msg\":\"error message is:"+"\n" + e.getMessage().trim() + "\n"+"\",\"status\":\"error\"}";
        }
        if ( i>0){
            return "{\"msg\":\"添加成功,user_idw为：" + i + "\",\"status\":\"ok\"}";
        }
        return "{\"msg\":\"未知\",\"status\":\"error\"}";

    }

    @RequestMapping(value = "/updateUser", method = {RequestMethod.POST} )
    public String updateAccount(AccountInfo accountInfo){
        accountService.updateAccount(accountInfo);
        return "{\"msg\":\"成功\",\"status\":\"OK\"}";

    }
    @RequestMapping(value = "/updateUser11", method = {RequestMethod.POST} )
    public String updateAccount11(){
        AccountInfo accountInfo = new AccountInfo();
        accountInfo.setAccount("hui.lu");
        accountInfo.setMobile("123123");
        accountInfo.setPassword("345345");
        accountService.updateAccount(accountInfo);
        return "{\"msg\":\"成功\",\"status\":\"OK\"}";

    }

    @RequestMapping(value = "/test", method = {RequestMethod.GET})
    public List<PlannedCasesForExecute>  getInfo1(){
        List<PlannedCasesForExecute> list =  getAllInfoForJob.getInfo();
        return  list;
    }

    @RequestMapping(value = "/test1", method = {RequestMethod.GET})
    public String getInfo() throws IOException {
        OkHttpUtil okHttpUtil = new OkHttpUtil();
        Map<String,Object> param = new HashMap<String, Object>();
        param.put("id","1");
        param.put("name","okhttp111");
        param.put("code","234234");
        String result = okHttpUtil.doPost("http://localhost:8090","/service/update",param,"");
        System.out.println(result);
        return result;
    }
    @Autowired
    Config config;
    @RequestMapping(value = "/test2", method = {RequestMethod.GET})
    public JsonResponse test2(){
        JsonResponse jsonResponse = new JsonResponse();

        jsonResponse.setMsg(config.getMYSQL_PASSWORD()+config.getMYSQL_URL()+config.getMYSQL_USERNAME());
        return jsonResponse;
    }
}
