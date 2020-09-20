package com.wangx.order001.config;

import feign.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * feign调用日志监控
 */
@Configuration
public class FeignConfig {
    @Bean
    Logger.Level feignLogLevel() {
        return Logger.Level.FULL;
    }
}
