package com.huilu.testplatform.entity.dao;

import lombok.Data;

/**
 * @Author:0xOO
 * @Date: 2018/9/26 0026
 * @Time: 14:39
 */
@Data
public class AccountInfo {
    private Integer id;
    private String account;
    private String password;
    private String createtime;
    private String realname;
    private String mobile;
}
