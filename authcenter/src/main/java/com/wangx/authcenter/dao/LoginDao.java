package com.wangx.authcenter.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface LoginDao {
    @Select("select count(*) from user_info where user_id=#{userId} and password=#{password}  ")
    Integer validateUser(@Param("userId") String userId, @Param("password") String password);
}
