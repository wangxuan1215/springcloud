package com.wangx.user002.feign;

import com.wangx.user002.common.Result;

public class AuthcenterFeignFallback implements AuthcenterFeign {


    @Override
    public Result register(String userId) {
        return null;
    }
}
