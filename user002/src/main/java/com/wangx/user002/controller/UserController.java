package com.wangx.user002.controller;

import com.alibaba.fastjson.JSON;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.wangx.user002.common.Result;
import com.wangx.user002.modle.UserVo;
import com.wangx.user002.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
@RefreshScope //刷新config配置
public class UserController {

    @Autowired
    private UserService userService;
    private Logger logger = LoggerFactory.getLogger(UserController.class);


    /**
     * 注册用户
     */
    @PostMapping("/register")
    public Result registerUser(UserVo userVo) {
        try {
            logger.info("=====调用注册用户接口成功 url{} param {}", "/register", JSON.toJSONString(userVo));
            return Result.defaultSuccess(userService.registerUser(userVo));
        } catch (RuntimeException b) {
            logger.error("=====调用注册用户接口失败 url{}" + "/register" + b.getMessage(), b);
            return Result.failure(0, b.getMessage());
        } catch (Exception e) {
            logger.error("=====调用注册用户接口失败 url{}" + "/register" + e.getMessage(), e);
            return Result.failure(0, e.getMessage());
        }
    }




    @GetMapping("/getUserInfo/{userId}")
    @HystrixCommand(fallbackMethod = "feignUserFallback", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "10000")
    }
//            , commandProperties = {
//            @HystrixProperty(name = "circuitBreaker.enabled", value = "true"),//开启熔断
//            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10"),//请求次数
//            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliSeconds", value = "10000"),//时间窗口
//            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "90"),//错误率
//    }
    )
    public String getUserInfo(@PathVariable String userId) {
        try {
            logger.info("======>>>>>>订单id查询用户id-调用 url:{{}} params:{}", "/remote/getUserInfo", userId);
            return userService.getUserInfo(userId);
        } catch (RuntimeException b) {
            logger.error("<<<<<<======订单id查询用户id-出错 reason:{}", "/remote/getUserInfo", b);
            return "500";
        } catch (Exception e) {
            logger.error("<<<<<<======订单id查询用户id-出错 reason:{}", "/remote/getUserInfo", e);
            return "500";
        }
    }

    //*****降级方法参数需要一致
    public String feignUserFallback(@PathVariable String userId) {
        return "user002网络不可用，请稍后重试";
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
