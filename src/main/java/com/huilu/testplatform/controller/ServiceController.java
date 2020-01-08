package com.huilu.testplatform.controller;

import com.huilu.testplatform.config.ErrorCode;
import com.huilu.testplatform.config.StatusCode;
import com.huilu.testplatform.entity.dao.ServiceManagement;
import com.huilu.testplatform.mapper.ServiceManagementMapper;
import com.huilu.testplatform.service.ServiceManagementService;
import com.huilu.testplatform.util.DateUtil;
import com.huilu.testplatform.util.JsonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/service")
public class ServiceController {
    @Autowired
    ServiceManagementMapper serviceManagementMapper;

    @Autowired
    ServiceManagementService serviceManagementService;

    @RequestMapping(value = "/getAll",method = {RequestMethod.GET})
    public JsonResponse getAll(){
        JsonResponse jsonResponse = new JsonResponse();
        return jsonResponse.setContent(serviceManagementMapper.queryAll());
    }

    @RequestMapping(value = "/addService",method = {RequestMethod.POST})
    public JsonResponse addService(ServiceManagement serviceManagement){
        JsonResponse jsonResponse = new JsonResponse();
        if (serviceManagementService.checkParams(serviceManagement)==false){
            return jsonResponse.setMsg("host或者name不能为空").setStatus(StatusCode.ERROR);
        }
        String date = DateUtil.getDate();
        serviceManagement.setCreateTime(date);
        serviceManagement.setUpdateTime(date);
        int count = serviceManagementService.insertService(serviceManagement);
        if (count > 0){
            return jsonResponse.setMsg("success,更新了"+count+"条数据");
        }else {
            return jsonResponse.setMsg("插入失败").setStatus(StatusCode.ERROR);

        }
    }

    @RequestMapping(value = "/update",method = {RequestMethod.POST})
    public JsonResponse updateByid(ServiceManagement serviceManagement){
        JsonResponse jsonResponse = new JsonResponse();
        String date = DateUtil.getDate();
        System.out.println(date);
        serviceManagement.setUpdateTime(date);
        if (serviceManagement.getId() == 0){
            return jsonResponse.setMsg("id必传").setStatus(StatusCode.ERROR);
        }
        int count = 0;
        String a = "";
        try {
            count = serviceManagementService.updateServiceById(serviceManagement);

        }catch(RuntimeException e){
            a = e.getMessage();
        }
        if ( count > 0 ) {
            return jsonResponse.setMsg("success,更新了"+count+"条数据");
        }else {
            return jsonResponse.setMsg(a).setStatus(StatusCode.ERROR);
        }
    }

    @RequestMapping(value = "/getService",method = {RequestMethod.GET})
    public JsonResponse getOneService(int id){
        JsonResponse jsonResponse = new JsonResponse();
        if (id <= 0){
            jsonResponse.setStatus(StatusCode.ERROR).setMsg(ErrorCode.FAIL.getMsg());
        }else {
            jsonResponse.setContent(serviceManagementMapper.getOneService(id));
        }
        return jsonResponse;
    }
}
