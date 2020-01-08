package com.huilu.testplatform.util;

import com.huilu.testplatform.config.QueryMethod;
import com.huilu.testplatform.entity.dao.TestCases;

public class CommonUtil {
    public static boolean checkQueryMethod(TestCases testCases){
        boolean flag = false;
        if (testCases.getMethod().equals(QueryMethod.GET.toString()) || testCases.getMethod().equals(QueryMethod.POST.toString()) || testCases.getMethod().equals(QueryMethod.DELETE.toString())){
            flag = true;
        }
        return flag;
    }

    public static void main(String[] args) {
        System.out.println(QueryMethod.DELETE.toString());
    }
}
