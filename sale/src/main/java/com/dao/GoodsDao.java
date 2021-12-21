package com.dao;

import com.entity.Goods;
import com.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface GoodsDao {
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "goodName", column = "goodName"),
            @Result(property = "goodTitle", column = "goodTitle"),
            @Result(property = "goodImg", column = "goodImg"),
            @Result(property = "goodDetail", column = "goodDetail"),
            @Result(property = "price", column = "price"),
            @Result(property = "count", column = "count"),
            @Result(property = "startTime", column = "startTime")

    })

    @Select("SELECT * FROM goods")
    List<Goods> getGoodsList();

    @Select("SELECT * FROM goods where id = #{0}")
    Goods getGoodInfo(String id);

    @Update("UPDATE goods set count = #{count} where id = #{id}")
    void setCount(@Param("count")Integer count,@Param("id")String id);

}
