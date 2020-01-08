package com.huilu.testplatform.mapper;

import com.huilu.testplatform.entity.dao.TestPlan;
import org.apache.ibatis.annotations.Insert;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TestPlanMapper {
    List<TestPlan> getAllPlan();

    @Insert("insert into test_plan (service_id,schedule,plan_name,create_time,update_time,status) value  (#{serviceid},#{schedule},#{planname},#{createtime},#{updatetime},#{status})")
    int addPlan(TestPlan testPlan);

    int update(TestPlan testPlan);

    TestPlan getOnePlan(int id);

    List<TestPlan> getEnabledPlanByService(int serviceid);
}
