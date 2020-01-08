package com.huilu.testplatform.entity.dao;

import com.huilu.testplatform.config.QueryMethod;
import lombok.Data;
import lombok.Getter;

import static com.huilu.testplatform.config.QueryMethod.*;


@Data
public class ServiceManagement {
    private int id;
    private String code;
    private String name;
    private String host;
    private String comment;
    private String createTime;
    private String updateTime;


}
