package com.wangx.order001.service;

import com.wangx.order001.dao.OrderInfoDao;
import com.wangx.order001.feign.UserFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OrderInfoService {

    @Autowired
    private OrderInfoDao orderInfoDao;
    @Autowired
    private UserFeign userFeign;

    public String orderInfo(String id) {
        return orderInfoDao.orderInfo(id);
    }

    /**
     * orderId查询用户信息
     */
    public String getUserInfoByOrderId(String id) {
        Optional.ofNullable(id).orElseThrow(() -> new RuntimeException("订单号不能为空"));
        String userId = orderInfoDao.orderInfo(id);
        Optional.ofNullable(userId).orElseThrow(() -> new RuntimeException("订单用户不存在"));
        return userFeign.getUserInfo(userId);
    }
}
