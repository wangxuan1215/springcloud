package com.wangx.user001.service;

import com.wangx.user001.dao.UserDao;
import com.wangx.user001.modle.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    public String getUserInfo(String userId) {
        Optional.ofNullable(userId).orElseThrow(() -> new RuntimeException("userId不能为空"));
        return "user001服务：" + userDao.getUserInfo(userId);
    }

    /**
     * 注册用户
     */
    @Transactional(rollbackFor = Exception.class)
    public Integer insertUser(UserVo userVo) {
        Optional.ofNullable(userVo.getUserId()).orElseThrow(() -> new RuntimeException("账号为空"));
        Optional.ofNullable(userVo.getPassword()).orElseThrow(() -> new RuntimeException("密码为空"));
        Optional.ofNullable(userVo.getUserName()).orElseThrow(() -> new RuntimeException("名字为空"));
        return userDao.insertUser(userVo);
    }
}
