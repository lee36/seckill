package com.lee.seckillshop.controller;

import com.lee.seckillshop.componet.JedisTemplate;
import com.lee.seckillshop.exception.SeckillUserIdNotExistException;
import com.lee.seckillshop.model.User;
import com.lee.seckillshop.service.GoodSeckillService;
import com.lee.seckillshop.service.UserService;
import com.lee.seckillshop.vo.ResultResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author admin
 * @date 2018-10-15
 */
@RestController
@RequestMapping("/seckill")
public class SeckillGoodController {
    @Autowired
    private GoodSeckillService goodSeckillService;
    @Autowired
    private UserService userService;
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;
    @Autowired
    private JedisTemplate jedisTemplate;

    @PostMapping("/good")
    public Object seckillGood(@RequestParam("userId") Integer userId,
                              @RequestParam("seckGoodId") Integer seckGoodId, @RequestParam("status") Integer staus) throws Exception {
        User user = userService.findById(userId);
        if (user == null) {
            throw new SeckillUserIdNotExistException("用户不存在");
        }
        if (staus.equals(0) || staus.equals(2) || staus < 0 && staus > 2) {
            throw new SeckillUserIdNotExistException("秒杀商品状态异常");
        }
        boolean flag = goodSeckillService.seckillGood(seckGoodId, userId);
        //查找缓存中是否有redis
        boolean b = findSeckillGoodInRedis(seckGoodId);
        if (!b) {
            throw new SeckillUserIdNotExistException("秒杀商品不存在");
        }
        if (flag) {
            //直接返回结果
            return new ResultResponse(0, "正在抢购中", null);
        }
        return new ResultResponse(505, "抢购失败", null);
    }

    /**
     * 缓存中查找
     *
     * @param seckGoodId
     * @return
     */
    private boolean findSeckillGoodInRedis(Integer seckGoodId) throws Exception {
        List<LinkedHashMap<String, Object>> seckillGoods = jedisTemplate.get("seckill:goods", List.class);
        long count = seckillGoods.stream().filter(each -> {
            return each.get("id").equals(seckGoodId);
        }).count();
        return count == 1 ? true : false;
    }

    @MessageMapping("/messageReciver")
    public void messageReciver(String msg) {
        System.out.println(msg + "==========");
        simpMessagingTemplate.convertAndSend("/seckill/123", msg + "hahah");
    }
}
