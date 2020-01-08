package com.huilu.testplatform.mapper;

import com.huilu.testplatform.entity.dao.JobLog;
import org.apache.ibatis.annotations.Insert;
import org.springframework.stereotype.Repository;

@Repository
public interface JobLogMapper {
    @Insert("insert into job_log (plan_id,case_id,status,response,job_id,create_time) values (#{planid},#{caseid},#{status},#{response},#{jobid},#{createtime})")
    int addJobLog(JobLog jobLog);
}
