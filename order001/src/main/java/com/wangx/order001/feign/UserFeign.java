package com.wangx.order001.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * User服务的远程接口
 */
@Component
@FeignClient(value = "user", fallback = UserFeignFallback.class) //统一配置降级
//feign找到user服务，feign是相对于客户端使用，去远程调用提供者的服务,服务降级类UserFeignFallback
public interface UserFeign {

    @GetMapping("/getUserInfo/{userId}")
    String getUserInfo(@PathVariable String orderId);

}
