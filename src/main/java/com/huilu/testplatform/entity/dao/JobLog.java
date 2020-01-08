package com.huilu.testplatform.entity.dao;

import lombok.Data;

@Data
public class JobLog {
    private int planid;
    private int caseid;
    private int status;
    private String createtime;
    private String response;
    private int jobid;
    private int id;
}
