package com.wangx.user001.feign;

import com.wangx.user001.common.Result;

public class AuthcenterFeignFallback implements AuthcenterFeign {


    @Override
    public Result register(String userId) {
        return null;
    }
}
