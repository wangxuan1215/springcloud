package com.wangx.user001.feign;

import com.wangx.user001.common.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Component
@FeignClient(value = "authcenter", fallback = AuthcenterFeignFallback.class)
public interface AuthcenterFeign {
    @PostMapping("/register")
    Result register(@RequestParam("userId") String userId);
}
