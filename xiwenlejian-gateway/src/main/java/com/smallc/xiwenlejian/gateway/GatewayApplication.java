package com.smallc.xiwenlejian.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author yj8023xx
 * @version 1.0
 * @date 2023/7/14
 * @since com.smallc.xiwenlejian.gateway
 */
@SpringBootApplication(scanBasePackages = {"com.smallc.xiwenlejian"})
@EnableDiscoveryClient
public class GatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }

}