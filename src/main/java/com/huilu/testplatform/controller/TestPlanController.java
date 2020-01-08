package com.huilu.testplatform.controller;

import com.huilu.testplatform.config.ErrorCode;
import com.huilu.testplatform.config.StatusCode;
import com.huilu.testplatform.entity.dao.TestPlan;
import com.huilu.testplatform.entity.service.PlannedCasesForExecute;
import com.huilu.testplatform.mapper.TestPlanMapper;
import com.huilu.testplatform.service.TestPlanService;
import com.huilu.testplatform.util.DateUtil;
import com.huilu.testplatform.util.JsonResponse;
import com.huilu.testplatform.zmyquartz.InterfaceAutoTestScheduler;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/plan")
public class TestPlanController {
    @Autowired
    TestPlanMapper testPlanMapper;

    @Autowired
    TestPlanService testPlanService;

    @Autowired
    InterfaceAutoTestScheduler interfaceAutoTestScheduler;

    private Scheduler scheduler;


    @RequestMapping(value = "/getAll", method = {RequestMethod.GET})
    public JsonResponse getAllPlan() {
        return new JsonResponse().setContent(testPlanMapper.getAllPlan()).setMsg(ErrorCode.SUCCESS.getMsg());
    }

    @RequestMapping(value = "/addPlan", method = {RequestMethod.POST})
    public JsonResponse addPlan(TestPlan testPlan) {
        JsonResponse jsonResponse = new JsonResponse();
        boolean flag = testPlanService.checkParams(testPlan);
        if (flag == false) {
            return jsonResponse.setStatus(StatusCode.ERROR).setMsg(ErrorCode.FAIL.getMsg());
        }
        String date = DateUtil.getDate();
        testPlan.setCreatetime(date);
        testPlan.setUpdatetime(date);
        testPlan.setStatus(1);
        if (testPlanService.addPlan(testPlan) > 0) {
            return jsonResponse;
        } else {
            return jsonResponse.setStatus(StatusCode.ERROR).setMsg(ErrorCode.FAIL.getMsg());
        }
    }

    @RequestMapping(value = "/update", method = {RequestMethod.POST})
    public JsonResponse updateOne(TestPlan testPlan) {
        JsonResponse jsonResponse = new JsonResponse();
        if (testPlan.getId() <= 0) {
            return jsonResponse.setStatus(StatusCode.ERROR).setMsg(ErrorCode.FAIL.getMsg());
        }
        testPlan.setUpdatetime(DateUtil.getDate());
        int result = testPlanService.update(testPlan);
        if (result > 0) {
            return jsonResponse;
        } else {
            return jsonResponse.setStatus(StatusCode.ERROR).setMsg(ErrorCode.FAIL.getMsg());
        }
    }

    @RequestMapping(value = "/getOnePlan", method = {RequestMethod.GET})
    public JsonResponse getOnePlan(int id) {
        JsonResponse jsonResponse = new JsonResponse();
        if (id <= 0) {
            jsonResponse.setStatus(StatusCode.ERROR).setMsg(ErrorCode.FAIL.getMsg());
        } else {
            jsonResponse.setContent(testPlanMapper.getOnePlan(id));
        }
        return jsonResponse;
    }

    @RequestMapping(value = "/runPlan" ,method = {RequestMethod.POST})
    public JsonResponse runPlannedCases(@RequestParam(name = "planId") int planId){
        JsonResponse jsonResponse = new JsonResponse();
        PlannedCasesForExecute plannedCasesForExecute = testPlanService.getCasesForExcecute(planId);
        try {
            interfaceAutoTestScheduler.addInstantInterfaceAutoTestJob(plannedCasesForExecute);
        } catch (SchedulerException e) {
            e.printStackTrace();
            jsonResponse.setMsg(e.getMessage());
        }
        return jsonResponse;
    }

}
