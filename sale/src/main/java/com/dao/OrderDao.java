package com.dao;

import com.entity.Order;
import com.entity.User;
import org.apache.ibatis.annotations.*;

import java.sql.Date;
import java.sql.Timestamp;

@Mapper
public interface OrderDao {
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "userId", column = "userId"),
            @Result(property = "goodId", column = "goodId"),
            @Result(property = "address", column = "address"),
            @Result(property = "goodName", column = "goodName"),
            @Result(property = "count", column = "count"),
            @Result(property = "price", column = "price"),
            @Result(property = "status", column = "status"),
            @Result(property = "create_time", column = "create_time"),
            @Result(property = "pay_time", column = "pay_time")
    })

    @Select("SELECT * FROM saleOrder WHERE userId = #{userId} and goodId = #{goodId}")
    Order getOrder(@Param("userId")String userId, @Param("goodId")String goodId);

    @Insert("INSERT INTO saleorder(userId,goodId,address,goodName,count,price,status,create_time)" +
            "VALUES(#{userId},#{goodId},#{address},#{goodName},#{count},#{price},#{status},#{create_time})")
    int createOrder(@Param("userId")String userId, @Param("goodId")String goodId,
                    @Param("address")String address,
                    @Param("goodName")String goodName, @Param("count")Integer count,
                    @Param("price")Double price, @Param("status")Integer status,
                    @Param("create_time") Timestamp create_time);
}
