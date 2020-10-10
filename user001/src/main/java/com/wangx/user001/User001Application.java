package com.wangx.user001;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableEurekaClient
@EnableCircuitBreaker //降级、熔断
@EnableFeignClients
@MapperScan(basePackages = "com.wangx.user001.dao")
public class User001Application {

    public static void main(String[] args) {
        SpringApplication.run(User001Application.class, args);
    }

}
