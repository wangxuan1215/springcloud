package com.wangx.user002.service;

import com.wangx.user002.common.Result;
import com.wangx.user002.dao.UserDao;
import com.wangx.user002.feign.AuthcenterFeign;
import com.wangx.user002.modle.UserVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserDao userDao;
    @Autowired
    private AuthcenterFeign authcenterFeign;
    private Logger logger = LoggerFactory.getLogger(UserService.class);

    public String getUserInfo(String userId) {
        Optional.ofNullable(userId).orElseThrow(() -> new RuntimeException("userId不能为空"));
        return "user002服务：" + userDao.getUserInfo(userId);
    }

    /**
     * 注册用户
     */
    @Transactional(rollbackFor = Exception.class)
    public Result registerUser(UserVo userVo) {
        Optional.ofNullable(userVo.getUserId()).orElseThrow(() -> new RuntimeException("账号为空"));
        Optional.ofNullable(userVo.getPassword()).orElseThrow(() -> new RuntimeException("密码为空"));
        Optional.ofNullable(userVo.getUserName()).orElseThrow(() -> new RuntimeException("名字为空"));
        Integer integer = userDao.insertUser(userVo);
        logger.info("用户注册成功：" + integer + " 准备创建token.......");
        return authcenterFeign.register(userVo.getUserId());
    }
}
