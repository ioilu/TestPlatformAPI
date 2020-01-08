package com.huilu.testplatform.entity.service;

import com.huilu.testplatform.entity.dao.TestCases;
import lombok.Data;

@Data
public class RunSingleCase {
    private String host;
    private TestCases testCases;
}
