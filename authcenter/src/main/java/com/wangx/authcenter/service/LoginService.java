package com.wangx.authcenter.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static com.wangx.authcenter.util.JwtUtil.generateToken;

@Service
public class LoginService {

    @Autowired
    private RedisTemplate redisTemplate;
    @Value("${jwt.refresh.token.key.format}")
    private String jwtRefreshTokenKeyFormat;
    @Value("${jwt.blacklist.key.format}")
    private String jwtBlacklistKeyFormat;

    public Map<String, Object> login(String userId, String password) {
        Optional.ofNullable(userId).orElseThrow(() -> new RuntimeException("账号不能为空"));
        Optional.ofNullable(password).orElseThrow(() -> new RuntimeException("密码不能为空"));
        //todo 验证账号密码.....


        //生成token
        Map<String, Object> resultMap = new HashMap<>();
        String token = generateToken(userId);
        //生成refreshToken
        String refreshToken = UUID.randomUUID().toString().replaceAll("-", "");
        //保存refreshToken至redis，使用hash结构保存使用中的token以及用户标识
        String refreshTokenKey = String.format(jwtRefreshTokenKeyFormat, refreshToken);
        redisTemplate.opsForHash().put(token,
                "userId", userId);
        redisTemplate.opsForHash().put(token,
                "refreshToken", refreshToken);
        //refreshToken设置过期时间
        redisTemplate.expire(token,
                1, TimeUnit.HOURS);
        //返回结果
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("token", token);
        dataMap.put("refreshToken", refreshToken);
        resultMap.put("data", dataMap);
        return resultMap;
    }

}
