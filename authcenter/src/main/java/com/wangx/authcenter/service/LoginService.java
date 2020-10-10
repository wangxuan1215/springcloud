package com.wangx.authcenter.service;

import com.wangx.authcenter.dao.LoginDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static com.wangx.authcenter.util.JwtUtil.generateToken;

@Service
public class LoginService {

    @Autowired
    private LoginDao loginDao;
    @Autowired
    private RedisTemplate redisTemplate;

    @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> login(String userId, String password) {
        Optional.ofNullable(userId).orElseThrow(() -> new RuntimeException("账号不能为空"));
        Optional.ofNullable(password).orElseThrow(() -> new RuntimeException("密码不能为空"));
        //验证账号密码
        Integer count = loginDao.validateUser(userId, password);
        if (count == 0) {
            throw new RuntimeException("用户名密码错误");
        }
        //生成token
        Map<String, Object> resultMap = new HashMap<>();
        String token = generateToken(userId);
        //生成refreshToken
        String refreshToken = UUID.randomUUID().toString().replaceAll("-", "");
        //保存到redis
        redisTemplate.opsForHash().put(token,
                "userId", userId);
        redisTemplate.opsForHash().put(token,
                "refreshToken", refreshToken);
        //设置过期时间
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
