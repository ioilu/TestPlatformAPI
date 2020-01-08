package com.huilu.testplatform.config;

import lombok.Data;

public enum ErrorCode {
    SUCCESS("0","success"),
    FAIL("1","fail");

    private String code;
    private String msg;
    ErrorCode(String code, String msg){
        this.code = code;
        this.msg = msg;
    }
    public String getCode() {
        return this.code;
    }

    public String getMsg() {
        return this.msg;
    }
}


