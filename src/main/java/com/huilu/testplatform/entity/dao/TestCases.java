package com.huilu.testplatform.entity.dao;

import lombok.Data;

@Data
public class TestCases {
    private int id;
    private int serviceid;
    private String api;
    private String headers;
    private String params;
    private String method;
    private String createtime;
    private String updatetime;
    private String casename;
    private String before;
    private String after;

}
