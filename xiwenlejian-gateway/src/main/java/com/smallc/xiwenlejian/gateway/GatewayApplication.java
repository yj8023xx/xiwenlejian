package com.smallc.xiwenlejian.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author yj8023xx
 * @version 1.0
 * @date 2023/7/14
 * @since com.smallc.xiwenlejian.gateway
 */
@SpringBootApplication(scanBasePackages = {"com.smallc.xiwenlejian"})
public class GatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }

}