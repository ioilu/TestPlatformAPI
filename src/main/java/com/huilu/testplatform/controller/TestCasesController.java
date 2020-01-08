package com.huilu.testplatform.controller;

import com.huilu.testplatform.config.ErrorCode;
import com.huilu.testplatform.config.StatusCode;
import com.huilu.testplatform.entity.dao.ServiceManagement;
import com.huilu.testplatform.entity.dao.TestCases;
import com.huilu.testplatform.entity.dao.TestPlan;
import com.huilu.testplatform.entity.service.RunSingleCase;
import com.huilu.testplatform.mapper.JobLogMapper;
import com.huilu.testplatform.mapper.JobMapper;
import com.huilu.testplatform.mapper.ServiceManagementMapper;
import com.huilu.testplatform.mapper.TestCasesMapper;
import com.huilu.testplatform.service.TestCasesService;
import com.huilu.testplatform.util.CommonUtil;
import com.huilu.testplatform.util.DateUtil;
import com.huilu.testplatform.util.JsonResponse;
import com.huilu.testplatform.ztestframework.OkHttpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/case")
public class TestCasesController {
    @Autowired
    TestCasesMapper testCasesMapper;

    @Autowired
    TestCasesService testCasesService;

    @Autowired
    OkHttpService okHttpService;

    @Autowired
    JobMapper jobMapper;

    @Autowired
    JobLogMapper jobLogMapper;

    @Autowired
    ServiceManagementMapper serviceManagementMapper;



    @RequestMapping(value = "/getCaseDetail" ,method = {RequestMethod.GET})
    public JsonResponse getCase(int id){
        JsonResponse jsonResponse = new JsonResponse();
        if (id <= 0){
            jsonResponse.setStatus(StatusCode.ERROR).setMsg(ErrorCode.FAIL.getMsg());
        }else {
            jsonResponse.setContent(testCasesMapper.getOneCase(id));
        }
        return jsonResponse;
    }

    @RequestMapping(value = "/getServiceCase",method = {RequestMethod.GET})
    public JsonResponse getServiceCase(int serviceid){
        JsonResponse jsonResponse = new JsonResponse();
        if (serviceid <= 0){
            jsonResponse.setStatus(StatusCode.ERROR).setMsg(ErrorCode.FAIL.getMsg());
        }else {
            jsonResponse.setContent(testCasesMapper.getServiceCases(serviceid));
        }
        return jsonResponse;
    }

    @RequestMapping(value = "/updateCase",method = {RequestMethod.POST})
    public JsonResponse updateCase(TestCases testCases){
        JsonResponse jsonResponse = new JsonResponse();
        boolean flag = testCasesService.checkParams(testCases);
        if (flag == false){
            return jsonResponse.setStatus(StatusCode.ERROR).setMsg(ErrorCode.FAIL.getMsg());
        }
        if (CommonUtil.checkQueryMethod(testCases) == false){
            return jsonResponse.setError();
        }
        testCases.setUpdatetime(DateUtil.getDate());
        if (testCasesService.updateCase(testCases) <= 0){
            jsonResponse.setError();
        }
        return jsonResponse;
    }

    @RequestMapping(value = "addCase",method = {RequestMethod.POST})
    public JsonResponse addCase(TestCases testCases){
        JsonResponse jsonResponse = new JsonResponse();
        boolean flag = testCasesService.checkParams(testCases);
        if (flag == false){
            return jsonResponse.setError();
        }
        if (CommonUtil.checkQueryMethod(testCases) == false){
            return jsonResponse.setError();
        }
        String date = DateUtil.getDate();
        testCases.setUpdatetime(date);
        testCases.setCreatetime(date);
        if (testCasesService.addCase(testCases) <= 0){
            jsonResponse.setError();
        }
        return jsonResponse;
    }

    @RequestMapping(value = "/deleteCase",method = {RequestMethod.DELETE})
    public JsonResponse deleteCase(@RequestParam("id") int id){
        JsonResponse jsonResponse = new JsonResponse();
        if (id <= 0) {
            return jsonResponse.setError();
        }
        if (testCasesService.deleteCase(id) <=0){
            jsonResponse.setError();
        }
        return jsonResponse;
    }

    @RequestMapping(value = "/runCase" , method = {RequestMethod.POST})
    public JsonResponse runCase(int caseId){
        JsonResponse jsonResponse = new JsonResponse();
        TestCases testCases = testCasesMapper.getOneCase(caseId);
        ServiceManagement serviceManagement = serviceManagementMapper.getOneService(testCases.getServiceid());
        String result = okHttpService.runSingleCase(serviceManagement.getHost(),testCases);
        return jsonResponse.setMsg(result);
    }

}
