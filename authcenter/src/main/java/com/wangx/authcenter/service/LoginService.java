package com.wangx.authcenter.service;

import org.apache.commons.lang.StringUtils;
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
        //验证账号密码
//        if (!StringUtils.equals(userName, "admin") || !StringUtils.equals(password, "123")) {
//            throw new RuntimeException("账号或者密码输入有误");
//        }
        Map<String, Object> resultMap = new HashMap<>();
        //生成JWT
        String token = generateToken(userId);
        //生成refreshToken
        String refreshToken = UUID.randomUUID().toString().replaceAll("-", "");
        //保存refreshToken至redis，使用hash结构保存使用中的token以及用户标识
        String refreshTokenKey = String.format(jwtRefreshTokenKeyFormat, refreshToken);
        redisTemplate.opsForHash().put(refreshTokenKey,
                "token", token);
        redisTemplate.opsForHash().put(refreshTokenKey,
                "userId", userId);
        //refreshToken设置过期时间
        redisTemplate.expire(refreshTokenKey,
                2, TimeUnit.HOURS);
        //返回结果
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("token", token);
        dataMap.put("refreshToken", refreshToken);
        resultMap.put("data", dataMap);
        return resultMap;
    }


    /**
     * 刷新token
     */
    public Map<String, Object> refreshToken(String refreshToken) {
        Map<String, Object> resultMap = new HashMap<>();
        String refreshTokenKey = String.format(jwtRefreshTokenKeyFormat, refreshToken);
        String userId = (String) redisTemplate.opsForHash().get(refreshTokenKey,
                "userId");
        if (StringUtils.isBlank(userId)) {
            resultMap.put("code", "500");
            resultMap.put("msg", "refreshToken过期");
            return resultMap;
        }
        String newToken = generateToken(userId);
        //替换当前token，并将旧token添加到黑名单
        String oldToken = (String) redisTemplate.opsForHash().get(refreshTokenKey,
                "token");
        redisTemplate.opsForHash().put(refreshTokenKey, "token", newToken);
        redisTemplate.opsForValue().set(String.format(jwtBlacklistKeyFormat, oldToken), "",
                1, TimeUnit.HOURS);
        resultMap.put("code", "200");
        resultMap.put("data", newToken);
        return resultMap;
    }
}
