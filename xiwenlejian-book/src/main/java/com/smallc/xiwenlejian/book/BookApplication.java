package com.smallc.xiwenlejian.book;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author yj8023xx
 * @version 1.0
 * @date 2023/7/11
 * @since com.smallc.xiwenlejian.book
 */
@SpringBootApplication(scanBasePackages = { "com.smallc.xiwenlejian" })
@EnableFeignClients(basePackages = {"com.smallc.xiwenlejian.api.**.feign"})
public class BookApplication {

    public static void main(String[] args) {
        SpringApplication.run(BookApplication.class, args);
    }

}
