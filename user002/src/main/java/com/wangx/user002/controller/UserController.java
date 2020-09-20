package com.wangx.user002.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
@RefreshScope //刷新config配置
public class UserController {

    @GetMapping("/getUserInfo")
    @HystrixCommand(fallbackMethod = "feignUserFallback"
//            , commandProperties = {
//            @HystrixProperty(name = "circuitBreaker.enabled", value = "true"),//开启熔断
//            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10"),//请求次数
//            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliSeconds", value = "10000"),//时间窗口
//            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "90"),//错误率
//    }
    )
    public String getUserInfo() {
        //测试本地服务超时，自动降级
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "这是用户服务2";
    }

    public String feignUserFallback() {
        return "网络不可用，请稍后重试";
    }

//    /**
//     * config统一配置中心文件内容
//     */
//    @Value("${env.version}")
//    private String msg;
//
//    @RequestMapping("/config")
//    public String index1() {
//        return "读取的配置文件是:" + msg;
//    }
}
