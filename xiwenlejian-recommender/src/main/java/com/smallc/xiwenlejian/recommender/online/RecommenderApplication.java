package com.smallc.xiwenlejian.recommender.online;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author yj8023xx
 * @version 1.0
 * @date 2023/7/13
 * @since com.smallc.xiwenlejian.recommender
 */
@SpringBootApplication(scanBasePackages = { "com.smallc.xiwenlejian.recommender.online" })
@EnableFeignClients(basePackages = {"com.smallc.xiwenlejian.api.**.feign"})
public class RecommenderApplication {

    public static void main(String[] args) {
        SpringApplication.run(RecommenderApplication.class, args);
    }

}
