package com.wangx.user002;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
@EnableCircuitBreaker //降级、熔断
public class User002Application {

    public static void main(String[] args) {
        SpringApplication.run(User002Application.class, args);
    }

}
