package com.ssafy.enjoytrip.configuration;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("com.ssafy.enjoytrip.*.model.mapper")
public class DBConfig {

}
