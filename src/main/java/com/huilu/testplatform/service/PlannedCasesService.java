package com.huilu.testplatform.service;

import com.huilu.testplatform.entity.dao.PlannedCases;
import com.huilu.testplatform.mapper.PlannedCasesMapper;
import com.huilu.testplatform.zmyquartz.UpdateTestCasesForExceuteSchedule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlannedCasesService {
    @Autowired
    UpdateTestCasesForExceuteSchedule updateTestCasesForExceuteSchedule;

    @Autowired
    PlannedCasesMapper plannedCasesMapper;

    public int addCases(List<PlannedCases> plannedCases){
        int result = plannedCasesMapper.addCases(plannedCases);
        updateTestCasesForExceuteSchedule.updateTestCasesForExceuteSchedule();
        return result;
    }

    public int deleteCases(List<PlannedCases> plannedCases){
        int result = plannedCasesMapper.deletePlanedCasesInPlan(plannedCases);
        updateTestCasesForExceuteSchedule.updateTestCasesForExceuteSchedule();
        return result;
    }

    public int deletePlanedCasesByCaseId(int caseid) {
        int result = plannedCasesMapper.deletePlanedCasesByCaseId(caseid);
        updateTestCasesForExceuteSchedule.updateTestCasesForExceuteSchedule();
        return result;
    }
}
