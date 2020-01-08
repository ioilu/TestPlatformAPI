package com.huilu.testplatform.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Data
@Component
public class Config {
    @Value("${spring.datasource.username}")
    private String MYSQL_USERNAME;

    @Value("${spring.datasource.password}")
    private String MYSQL_PASSWORD;

    @Value("${spring.datasource.url}")
    private String MYSQL_URL;
}
