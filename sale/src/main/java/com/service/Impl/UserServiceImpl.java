package com.service.Impl;

import com.alibaba.fastjson.JSON;
import com.dao.GoodsDao;
import com.dao.OrderDao;
import com.dao.UserDao;
import com.entity.Goods;
import com.entity.Message;
import com.entity.Order;
import com.entity.User;
import com.service.UserService;
import com.util.MD5;
import com.util.TokenUtil;
import com.vo.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Service
public class UserServiceImpl implements UserService, InitializingBean {
    private static Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserDao userDao;

    @Autowired
    private GoodsDao goodsDao;

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private DefaultRedisScript redisScript;

    public Set<String> set = new HashSet<>();

    //处理消息队列里的消息生成订单
    private ThreadPoolExecutor threadPoolSend = new ThreadPoolExecutor(
            30,
            60,
            0,
            TimeUnit.SECONDS,
            new ArrayBlockingQueue<Runnable>(100),
            Executors.defaultThreadFactory(),
            new ThreadPoolExecutor.CallerRunsPolicy()
    );



    @Override
    public Result login(String id,String password) {
        Result result = new Result();
        User user = userDao.getMd5Word(id);
        if(user != null){
            if(user.getPassword().equals(MD5.getMd5Word(password))){
                result.setCode(200);
                String token = TokenUtil.sign(id);
                result.setMsg(token);
                user.setPassword(null);
                result.setData(user);
                return result;
            }
        }
        result.setCode(400);
        result.setMsg("账号或密码错误！");
        return result;
    }

    public Result getGoodsList(){
        Result result = new Result();
        List<Goods> goods = goodsDao.getGoodsList();
        result.setCode(200);
        result.setData(goods);
        return result;
    }


    public Result getGoodInfo(String id){
        Result result = new Result();
        Goods goods = goodsDao.getGoodInfo(id);
        result.setData(goods);
        result.setCode(200);
        return result;
    }

    public Result buy(String id,String goodId){
        Result result = new Result();
        if(set.contains(goodId)){
            result.setCode(300);
            result.setMsg("已抢光！");
            return result;
        }
        ValueOperations valueOperations = redisTemplate.opsForValue();
        String value = String.valueOf(UUID.randomUUID());
        Boolean lock = valueOperations.setIfAbsent(goodId + "1",value);
        if(lock){
            redisTemplate.expire(goodId + "1",2,TimeUnit.SECONDS);
            if(valueOperations.get(id + ":" + goodId) == null){
                Long count = valueOperations.increment(goodId,-1);
                if(count < 0){
                    valueOperations.increment(goodId, 1);
                    set.add(goodId);
                    result.setCode(300);
                    result.setMsg("已抢光！");
                    return result;
                }
                valueOperations.set(id + ":" + goodId, "1");
                log.info("用户：" + id + ",商品：" + goodId);
                Message msg = new Message();
                msg.setUserId(id);
                msg.setGoodId(goodId);
                threadPoolSend.execute(() -> rabbitTemplate.convertAndSend("change","seckill", JSON.toJSONString(msg)));
                result.setCode(200);
                result.setMsg("成功了！");
            }else{
                result.setCode(300);
                result.setMsg("请勿重复抢购！");
            }
            redisTemplate.execute(redisScript, Collections.singletonList(goodId + "1"), value);
        }else{
            result.setCode(400);
            result.setMsg("请重试！");
        }
        return result;
    }

    @Override
    public Result createOrder(String userId, String goodId, String goodName, Double price, Timestamp createTime) {
        Result result = new Result();
        try {
            int res = orderDao.createOrder(userId, goodId, "北京",goodName, 1,
                    price, 1, createTime);
            result.setData(res);
        }catch (Exception e){
            result.setData(0);
            System.out.println("不能重复下单!");
        }
        return result;
    }

    @Override
    public int setCount(Integer count, String id) {
        goodsDao.setCount(count, id);
        return 1;
    }

    @Override
    public Result getOrder(String userId, String goodId) {
        Result result = new Result();
        Order order = orderDao.getOrder(userId, goodId);
        if(order != null){
            result.setCode(200);
            result.setData(order);
        }else{
            result.setCode(400);
        }

        return result;
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        List<Goods> goods = goodsDao.getGoodsList();
        if(!goods.isEmpty()){
            for(int i = 0; i < goods.size(); i++){
                redisTemplate.opsForValue().set(goods.get(i).getId(), String.valueOf(goods.get(i).getCount()));
            }
        }
    }
}
