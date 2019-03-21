package com.lee.seckillshop.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lee.seckillshop.commons.componet.JedisComponet;
import com.lee.seckillshop.commons.exception.SeckillUserIdNotExistException;
import com.lee.seckillshop.commons.model.SeckillGood;
import com.lee.seckillshop.commons.model.User;
import com.lee.seckillshop.service.GoodSeckillService;
import com.lee.seckillshop.service.UserService;
import com.lee.seckillshop.commons.vo.ResultResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

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
    private JedisComponet jedisTemplate;

    @GetMapping("/good")
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

    @PostMapping("/add")
    public Object addSeckillGood(@RequestBody SeckillGood good){
        Boolean flag = goodSeckillService.addSeckillGood(good);
        if(flag) {
            return new ResultResponse(200, "success", flag);
        }
        return new ResultResponse(500, "error", flag);
    }

    /**
     *
     * @param status 0:禁用,1:可用
     * @param id 秒杀商品id
     * @return
     */
    @GetMapping("/update")
    public Object updateStatus(Integer status,Integer id){
         Boolean flag=goodSeckillService.updateSeckillGood(status,id);
         if(flag){
             return new ResultResponse(200, "success", flag);
         }
        return new ResultResponse(500, "error", flag);
    }

    @GetMapping("/myselfSeckill")
    public Object myselfSeckill(@PageableDefault(page = 1,size = 2) Pageable pageable,Integer id) {
        PageHelper.startPage(pageable.getPageNumber(), pageable.getPageSize());
        List<SeckillGood> seckillGoods = goodSeckillService.getMySelfGoodsList(id);
        if (CollectionUtils.isEmpty(seckillGoods)) {
            return new ResultResponse(500, "没有任何内容", null);
        }
        return new ResultResponse(200, "success", new PageInfo<SeckillGood>(seckillGoods));
    }
    @GetMapping("/delSeckill")
    public Object delUser(String seckills) {
        String s = seckills.substring(1, seckills.length() - 1);
        List<Integer> ids = Arrays.asList(s.split(",")).
                stream().map(one -> Integer.parseInt(one)).collect(Collectors.toList());
        Boolean flag = goodSeckillService.deleteSelected(ids);
        if (flag) {
            return new ResultResponse(200, "success", flag);
        }
        return new ResultResponse(500, "error", null);
    }
}
