package com.huilu.testplatform.service;

import com.huilu.testplatform.entity.dao.PlannedCases;
import com.huilu.testplatform.entity.dao.ServiceManagement;
import com.huilu.testplatform.entity.dao.TestCases;
import com.huilu.testplatform.entity.dao.TestPlan;
import com.huilu.testplatform.entity.service.PlannedCasesForExecute;
import com.huilu.testplatform.mapper.PlannedCasesMapper;
import com.huilu.testplatform.mapper.ServiceManagementMapper;
import com.huilu.testplatform.mapper.TestCasesMapper;
import com.huilu.testplatform.mapper.TestPlanMapper;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Data
@Component
public class GetAllInfoForJob {
    @Autowired
    ServiceManagementMapper serviceManagementMapper;

    @Autowired
    TestPlanMapper testPlanMapper;

    @Autowired
    TestCasesMapper testCasesMapper;

    @Autowired
    PlannedCasesMapper plannedCasesMapper;

    private List<ServiceManagement> serviceManagementList;
    private List<TestPlan> testPlanList;
    private List<TestCases> testCasesList;
    private List<PlannedCases> plannedCasesList;





    public List<PlannedCasesForExecute> getInfo(){
        List<PlannedCasesForExecute> plannedCasesForExecuteList = new ArrayList<PlannedCasesForExecute>();
        this.serviceManagementList = serviceManagementMapper.queryAll();
        if (serviceManagementList == null || serviceManagementList.size() < 1){
            return null;
        }

        for (ServiceManagement serviceManagement : serviceManagementList) {
            this.testPlanList = testPlanMapper.getEnabledPlanByService(serviceManagement.getId());
            if (testPlanList == null || testPlanList.size() < 0){
                break;
            }

            for (TestPlan testPlan : testPlanList){
                this.plannedCasesList = plannedCasesMapper.getByPlan(testPlan.getId());
                if (plannedCasesList == null || plannedCasesList.size() < 1){
                    break;
                }

                this.testCasesList = testCasesMapper.getCasesByIDs(plannedCasesList);
                if (testCasesList == null || testCasesList.size() < 1){
                    break;
                }

                PlannedCasesForExecute plannedCasesForExecute = new PlannedCasesForExecute();
                plannedCasesForExecute.setServiceManagement(serviceManagement);
                plannedCasesForExecute.setTestPlan(testPlan);
                plannedCasesForExecute.setTestCasesList(testCasesList);
                plannedCasesForExecuteList.add(plannedCasesForExecute);
            }
        }
        return plannedCasesForExecuteList;
    }



}
