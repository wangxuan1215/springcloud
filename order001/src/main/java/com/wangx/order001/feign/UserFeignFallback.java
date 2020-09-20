package com.wangx.order001.feign;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;

@Component
public class UserFeignFallback implements UserFeign {

    @Override
    public String getUserInfo(@PathVariable String orderId) {
        return "网络异常，请稍后再试";
    }
}
