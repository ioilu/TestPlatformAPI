package com.huilu.testplatform.mapper;

import com.huilu.testplatform.entity.dao.PlannedCases;
import com.huilu.testplatform.entity.dao.TestCases;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TestCasesMapper {
    TestCases getOneCase(int id);

    List<TestCases> getServiceCases(int serviceid);

    int updateCase(TestCases testCases);

    @Insert("insert into test_cases (service_id,api,case_name,headers,params,method,create_time,update_time,before,after) value (#{serviceid},#{api},#{casename},#{headers},#{params},#{method},#{createtime},#{updatetime},#{before},#{after})")
    int addCase(TestCases testCases);

    @Delete("delete from test_cases where id = #{id}")
    int deleteCase(int id);

    List<TestCases> getCasesByIDs(List<PlannedCases> plannedCasesList);
}
