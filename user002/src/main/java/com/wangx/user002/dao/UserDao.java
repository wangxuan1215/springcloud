package com.wangx.user002.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserDao {
    @Select("select user_name from user_info where user_id=#{userId}")
    String getUserInfo(String userId);
}
