package com.wangx.user002.dao;

import com.wangx.user002.modle.UserVo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserDao {
    @Select("select user_name from user_info where user_id=#{userId}")
    String getUserInfo(String userId);

    @Insert("insert into user_info (user_id,user_name,user_sex,password) values (#{userId},#{userName},#{userSex},#{password})")
    Integer insertUser(UserVo userVo);
}
