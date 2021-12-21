package com.controller;

import com.service.UserService;
import com.util.TokenUtil;
import com.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Random;

/**
 *
 */
@Controller
@ResponseBody
@Slf4j
public class UserController {
    private static Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    /*@Scheduled(fixedRate = 1)
    public void log() {
        logger.info("Logging...");
    }*/

    @PostMapping(value = "/login")
    public Result login(@RequestParam String id, @RequestParam String password) throws InterruptedException {
        logger.info("laile111111111111111111111111111111111");
        return userService.login(id, password);
    }

    @PostMapping(value = "/goodsList")
    public Result getGoodsList(){
        return userService.getGoodsList();
    }

    @PostMapping(value = "/goodInfo")
    public Result getGoodInfo(@RequestParam String id){
        return userService.getGoodInfo(id);
    }

    @PostMapping(value = "/buy")
    public Result buy(HttpServletRequest request, @RequestParam String goodId){
        /*int choice = new Random().nextInt();
        if(choice % 2 == 0){
            return null;
        }*/
        System.out.println(request.getHeader("token"));
        String id = TokenUtil.parseJWT(request.getHeader("token"));

        System.out.println(id);
        System.out.println(goodId);
        return userService.buy(id,goodId);
    }

    @PostMapping(value = "/orderInfo")
    public Result getOrder(HttpServletRequest request,@RequestParam String goodId){
        String id = TokenUtil.parseJWT(request.getHeader("token"));
        return userService.getOrder(id, goodId);
    }

}
