package com.mq;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.entity.Goods;
import com.entity.Message;
import com.entity.Order;
import com.service.UserService;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component

public class MsgConsumer {

    @Autowired
    private UserService userService;


    @RabbitHandler
    @RabbitListener(queues = "nihao")
    @Transactional
    public void received(String msg) {
        JSONObject mes = JSONObject.parseObject(msg);
        Message message = JSON.toJavaObject(mes,Message.class);
        System.out.println(message.getUserId() + " 你好 啊");
        String goodId = message.getGoodId();
        String id = message.getUserId();
        Goods goods = (Goods) userService.getGoodInfo(goodId).getData();
        Date date = new Date();
        String goodName = goods.getGoodName();
        Double price = goods.getPrice();
        long longTime = date.getTime();
        Timestamp timestamp = new Timestamp(longTime);
        int count = goods.getCount();
        if(count > 0){
            int res = (Integer) userService.createOrder(id,goodId,goodName,price, timestamp).getData();
            if( res == 1){
                userService.setCount(count - 1,goodId);
            }
        }
    }

    public static void main(String[] args) {
        Date date = new Date();
        long longTime = date.getTime();
        Timestamp timestamp = new Timestamp(longTime);
        System.out.println(date);
    }
}
