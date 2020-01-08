package com.huilu.testplatform.controller;

import com.huilu.testplatform.entity.dao.PlannedCases;
import com.huilu.testplatform.service.PlannedCasesService;
import com.huilu.testplatform.util.JsonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/plannedCases")
public class PlannedCasesController {
    @Autowired
    PlannedCasesService plannedCasesService;

    @RequestMapping(value = "/addCases",method = {RequestMethod.POST})
    public JsonResponse addCases(@RequestBody List<PlannedCases> plannedCasesList){
        JsonResponse jsonResponse = new JsonResponse();
        plannedCasesService.addCases(plannedCasesList);
        return  jsonResponse;
    }

    @RequestMapping(value = "/deleteOnePlanCases",method = {RequestMethod.DELETE})
    public JsonResponse deleteCasesInPlan(@RequestBody List<PlannedCases> plannedCasesList){
        plannedCasesService.deleteCases(plannedCasesList);
        return new JsonResponse();
    }

}
