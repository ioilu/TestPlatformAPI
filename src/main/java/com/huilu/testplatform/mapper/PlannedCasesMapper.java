package com.huilu.testplatform.mapper;

import com.huilu.testplatform.entity.dao.PlannedCases;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlannedCasesMapper {
    int addCases(List<PlannedCases> plannedCases);

    int deletePlanedCasesInPlan(List<PlannedCases> plannedCases);

    int deletePlanedCasesByCaseId(int id);

    List<PlannedCases> getByPlan(int planid);
}
