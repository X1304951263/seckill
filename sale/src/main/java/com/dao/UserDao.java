package com.dao;

import com.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserDao {
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "password", column = "password"),
            @Result(property = "nickName", column = "nickName")
    })

    @Select("SELECT * FROM user WHERE id = #{0}")
    User getMd5Word(String id);
}
