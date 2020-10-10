package com.wangx.authcenter.service;

import com.wangx.authcenter.dao.LoginDao;
import com.wangx.authcenter.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
@Transactional(rollbackFor = Exception.class)
public class LoginService {

    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private LoginDao loginDao;
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 登陆
     */
    public Map<String, Object> login(String userId, String password) {
        Optional.ofNullable(userId).orElseThrow(() -> new RuntimeException("账号不能为空"));
        Optional.ofNullable(password).orElseThrow(() -> new RuntimeException("密码不能为空"));
        //验证账号密码
        Integer count = loginDao.validateUser(userId, password);
        if (count == 0) {
            throw new RuntimeException("用户名密码错误");
        }
        return jwtData(userId);
    }

    /**
     * 注册
     */
    public Map<String, Object> register(String userId) {
        Optional.ofNullable(userId).orElseThrow(() -> new RuntimeException("账号不能为空"));
        return jwtData(userId);
    }

    /**
     * 封装jwt数据
     */
    private Map<String, Object> jwtData(String userId) {
        Map<String, Object> resultMap = new HashMap<>();
        Map<String, Object> jwtMap = jwtUtil.generateToken(userId);
        String token = String.valueOf(jwtMap.get("token"));
        String refreshToken = String.valueOf(jwtMap.get("refreshToken"));
        //保存到redis
        redisTemplate.opsForHash().put(token,
                "userId", userId);
        redisTemplate.opsForHash().put(token,
                "refreshToken", refreshToken);
        //设置过期时间
        redisTemplate.expire(token,
                1, TimeUnit.HOURS);
        //返回结果
        resultMap.put("data", jwtMap);
        return resultMap;
    }
}
