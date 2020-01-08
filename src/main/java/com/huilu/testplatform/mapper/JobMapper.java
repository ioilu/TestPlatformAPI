package com.huilu.testplatform.mapper;

import com.huilu.testplatform.entity.dao.Job;
import org.springframework.stereotype.Repository;

@Repository
public interface JobMapper {
    int addJob(Job job);
}
