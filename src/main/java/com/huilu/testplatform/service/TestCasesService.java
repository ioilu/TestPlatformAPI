package com.huilu.testplatform.service;

import com.huilu.testplatform.entity.dao.TestCases;
import com.huilu.testplatform.mapper.TestCasesMapper;
import com.huilu.testplatform.util.ParamsUtil;
import com.huilu.testplatform.util.mysql.JdbcUtils;
import com.huilu.testplatform.zmyquartz.UpdateTestCasesForExceuteSchedule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TestCasesService {
    @Autowired
    UpdateTestCasesForExceuteSchedule updateTestCasesForExceuteSchedule;

    @Autowired
    TestCasesMapper testCasesMapper;

    @Autowired
    PlannedCasesService plannedCasesService;

    @Autowired
    JdbcUtils jdbcUtils;

    public boolean checkParams(TestCases testCases){
        boolean flag = true;
        if (testCases.getApi() == null || testCases.getApi() == "" || testCases.getCasename() == null || testCases.getCasename() == "" || testCases.getMethod() == null ){
            flag = false;
        }
        return flag;
    }

    public int updateCase(TestCases testCases){
        int result = testCasesMapper.updateCase(testCases);
        updateTestCasesForExceuteSchedule.updateTestCasesForExceuteSchedule();
        return result;
    }

    public int addCase(TestCases testCases){
        int result = testCasesMapper.addCase(testCases);
        updateTestCasesForExceuteSchedule.updateTestCasesForExceuteSchedule();
        return result;
    }

    public int deleteCase(int caseid){
        int result = testCasesMapper.deleteCase(caseid);
        plannedCasesService.deletePlanedCasesByCaseId(caseid);
        updateTestCasesForExceuteSchedule.updateTestCasesForExceuteSchedule();
        return result;
    }

    public void executeBefore(String beforeSql){
        String[] sqlStrings = ParamsUtil.toArray(beforeSql);
        for (String sql : sqlStrings){
            jdbcUtils.executeSql(sql);
        }
    }

    public void executeAfter(String afterSql){
        String[] sqlStrings = ParamsUtil.toArray(afterSql);
        for (String sql : sqlStrings){
            jdbcUtils.executeSql(sql);
        }
    }
}
