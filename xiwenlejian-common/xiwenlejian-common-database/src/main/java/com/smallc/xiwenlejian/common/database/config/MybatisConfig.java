package com.smallc.xiwenlejian.common.database.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author yj8023xx
 * @version 1.0
 * @date 2023/7/15
 * @since com.smallc.xiwenlejian.common.database.config
 */
@Configuration
@MapperScan({ "com.smallc.xiwenlejian.**.mapper" })
public class MybatisConfig {
}
