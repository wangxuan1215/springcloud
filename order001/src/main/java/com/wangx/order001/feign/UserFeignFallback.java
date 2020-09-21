package com.wangx.order001.feign;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;

@Component
public class UserFeignFallback implements UserFeign {

    @Override
    public String getUserInfo(@PathVariable String orderId) {
        return "order网络不可用，请稍后再试";
    }
}
