package com.huilu.testplatform.service;

import com.huilu.testplatform.entity.dao.AccountInfo;
import com.huilu.testplatform.mapper.AccountMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author:0xOO
 * @Date: 2018/9/26 0026
 * @Time: 15:23
 */
@Service
public class AccountService {
    @Autowired
    AccountMapper accountMapper;

    public AccountInfo getInfo(int id){
        return accountMapper.getInfo(id);
    }

    public int addUser(AccountInfo accountInfo){
        return accountMapper.addUser(accountInfo);
    }

    public boolean checkParams(AccountInfo accountInfo) {
        boolean flag = false;
        if (!accountInfo.getAccount().trim().isEmpty() && !accountInfo.getPassword().trim().isEmpty()){
            flag = true;
            return flag;
        }
        return flag;
    }

    public boolean checkAccountNotExist(AccountInfo accountInfo){
        boolean flag = true;
        AccountInfo info = accountMapper.getInfoByAccountAndMobile(accountInfo);
        if (info != null) {
            flag = false;
            return flag;
        }

        return flag;
    }

    public void updateAccount(AccountInfo accountInfo){
        accountMapper.updateUserById(accountInfo);
    }
}
