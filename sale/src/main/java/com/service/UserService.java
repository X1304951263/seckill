package com.service;

import com.vo.Result;

import java.sql.Timestamp;

public interface UserService {
    Result login(String id,String password);
    Result getGoodsList();
    Result getGoodInfo(String id);
    Result buy(String id,String goodId);
    Result createOrder(String userId, String goodId, String goodName, Double price, Timestamp createTime);

    int setCount(Integer count,String id);
    Result getOrder(String userId,String goodId);
}
