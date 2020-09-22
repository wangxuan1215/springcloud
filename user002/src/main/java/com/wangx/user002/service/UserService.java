package com.wangx.user002.service;

import com.wangx.user002.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    public String getUserInfo(String userId) {
        Optional.ofNullable(userId).orElseThrow(() -> new RuntimeException("userId不能为空"));
        return "user002服务：" + userDao.getUserInfo(userId);
    }
}
