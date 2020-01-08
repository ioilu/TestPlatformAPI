package com.huilu.testplatform.entity.dao;

import lombok.Data;

@Data
public class TestPlan {
    private int id;
    private int serviceid;
    private String schedule;
    private String planname;
    private String createtime;
    private String updatetime;
    private int status;
}
