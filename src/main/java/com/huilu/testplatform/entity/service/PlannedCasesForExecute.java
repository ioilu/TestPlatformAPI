package com.huilu.testplatform.entity.service;

import com.huilu.testplatform.entity.dao.ServiceManagement;
import com.huilu.testplatform.entity.dao.TestCases;
import com.huilu.testplatform.entity.dao.TestPlan;
import lombok.Data;

import java.util.List;

@Data
/**
 * 该实体类是为作业调度时使用
 */
public class PlannedCasesForExecute {
    private ServiceManagement serviceManagement;
    private TestPlan testPlan;
    private List<TestCases> TestCasesList;
}
