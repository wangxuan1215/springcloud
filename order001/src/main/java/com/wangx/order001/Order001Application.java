package com.wangx.order001;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
@EnableCircuitBreaker //降级、熔断
@MapperScan(basePackages="com.wangx.order001.dao")
////启动的时候自动加载自定义的负载均衡算法
//@RibbonClient(name="USER",configuration = WangXRule.class)
public class Order001Application {

    public static void main(String[] args) {
        SpringApplication.run(Order001Application.class, args);
    }

}
