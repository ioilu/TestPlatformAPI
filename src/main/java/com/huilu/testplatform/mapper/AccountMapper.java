package com.huilu.testplatform.mapper;

import com.huilu.testplatform.entity.dao.AccountInfo;
import org.springframework.stereotype.Repository;

/**
 * @Author:0xOO
 * @Date: 2018/9/26 0026
 * @Time: 15:20
 */
@Repository
public interface AccountMapper {
//    @Select("select * from account_info where id = #{id}")
    AccountInfo getInfo(int id);

    int addUser(AccountInfo accountInfo);

    AccountInfo getInfoByAccountAndMobile(AccountInfo accountInfo);

    void updateUserById(AccountInfo accountInfo);

}
