package com.wangx.order001.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface OrderInfoDao {
    @Select("select user_id from order_info")
    String orderInfo(String id);
}
