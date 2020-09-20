package com.wangx.order001.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {
    @Bean
    @LoadBalanced //配置负载均衡实现ResTemplate（默认的负载均衡算法）
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
