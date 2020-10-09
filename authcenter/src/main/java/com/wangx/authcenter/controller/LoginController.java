package com.wangx.authcenter.controller;

import com.wangx.authcenter.util.JwtUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RefreshScope //刷新config配置
@RequestMapping
public class LoginController {

    @RequestMapping("/login")
    public Map<String, Object> login(@RequestParam String userName,
                                     @RequestParam String password) {
        Map<String, Object> resultMap = new HashMap<>();
        //账号密码校验
        if (StringUtils.equals(userName, "admin") &&
                StringUtils.equals(password, "123")) {

            //生成JWT
            String token = JwtUtil.generateToken(userName);
            //生成refreshToken
            String refreshToken = UUID.randomUUID().toString().replaceAll("-", "");
//            //保存refreshToken至redis，使用hash结构保存使用中的token以及用户标识
//            String refreshTokenKey = String.format(jwtRefreshTokenKeyFormat, refreshToken);
//            stringRedisTemplate.opsForHash().put(refreshTokenKey,
//                    "token", token);
//            stringRedisTemplate.opsForHash().put(refreshTokenKey,
//                    "userName", userName);
//            //refreshToken设置过期时间
//            stringRedisTemplate.expire(refreshTokenKey,
//                    refreshTokenExpireTime, TimeUnit.MILLISECONDS);
            //返回结果
            Map<String, Object> dataMap = new HashMap<>();
            dataMap.put("token", token);
            dataMap.put("refreshToken", refreshToken);
            resultMap.put("code", "10000");
            resultMap.put("data", dataMap);
            return resultMap;
        }
        resultMap.put("isSuccess", false);
        return resultMap;
    }


}
