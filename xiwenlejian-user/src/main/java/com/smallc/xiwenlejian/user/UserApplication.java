package com.smallc.xiwenlejian.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author yj8023xx
 * @version 1.0
 * @date 2023/7/11
 * @since com.smallc.xiwenlejian.user
 */
@SpringBootApplication(scanBasePackages = { "com.smallc.xiwenlejian" })
@EnableFeignClients(basePackages = {"com.smallc.xiwenlejian.api.**.feign"})
public class UserApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class, args);
    }

}
