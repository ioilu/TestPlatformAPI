package com.huilu.testplatform.service;

import com.huilu.testplatform.entity.dao.ServiceManagement;
import com.huilu.testplatform.mapper.ServiceManagementMapper;
import com.huilu.testplatform.zmyquartz.UpdateTestCasesForExceuteSchedule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServiceManagementService {
    @Autowired
    ServiceManagementMapper serviceManagementMapper;

    @Autowired
    UpdateTestCasesForExceuteSchedule updateTestCasesForExceuteSchedule;

    public  boolean checkParams(ServiceManagement serviceManagement){
        boolean b = true;
        if (serviceManagement.getHost()==null || serviceManagement.getName() == null || serviceManagement.getHost() == ""){
            b = false;
        }
        return b;
    }

    public int insertService(ServiceManagement serviceManagement){
        int result = serviceManagementMapper.insertService(serviceManagement);
        updateTestCasesForExceuteSchedule.updateTestCasesForExceuteSchedule();
        return result;
    }

    public int updateServiceById(ServiceManagement serviceManagement){
        int result = serviceManagementMapper.updateServiceById(serviceManagement);
        updateTestCasesForExceuteSchedule.updateTestCasesForExceuteSchedule();
        return result;
    }

}
