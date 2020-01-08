package com.huilu.testplatform.zmyquartz;

import com.huilu.testplatform.service.GetAllInfoForJob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

//可以通过定时任务来更新需要执行的计划以及用例信息对象，不可取，会增大消耗
@Component
public class UpdateTestCasesForExceuteSchedule {
    @Autowired
    GetAllInfoForJob getAllInfoForJob;

    @Autowired
    InterfaceAutoTestScheduler interfaceAutoTestScheduler;


//    @Scheduled(cron="*/30 * * * * ?")
    public void updateTestCasesForExceuteSchedule(){
        interfaceAutoTestScheduler.plannedCasesForExecuteList = getAllInfoForJob.getInfo();
    }
}
