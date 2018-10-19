package com.lee.seckillshop.service.impl;

import com.lee.seckillshop.componet.JedisTemplate;
import com.lee.seckillshop.mapper.SeckillGoodsMapper;
import com.lee.seckillshop.mapper.SeckillOrderMapper;
import com.lee.seckillshop.model.SeckillGood;
import com.lee.seckillshop.model.SeckillOrder;
import com.lee.seckillshop.model.User;
import com.lee.seckillshop.service.GoodSeckillService;
import com.lee.seckillshop.util.CommonUtil;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.annotation.RabbitListeners;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @author admin
 * @date 2018-10-15
 */
@Service
public class GoodSeckillServiceImpl implements GoodSeckillService {
    @Autowired
    private SeckillGoodsMapper seckillGoodsMapper;
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private JedisTemplate jedisTemplate;
    @Autowired
    private SeckillOrderMapper seckillOrderMapper;
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;


    @Async
    @RabbitListener(queues = "queque")
    public void dealSeckillDeal(Map<String,Object> ids) throws Exception {
        System.out.println("进来了啊啊啊");
        //生成订单
        Integer seckGoodId=(Integer) ids.get("seckGoodId");
        Integer userId=(Integer)ids.get("userId");
//        try {
//            createOrder(userId,seckGoodId);
//        } catch (Exception e) {
//            System.out.println("抢购失败");
//            simpMessagingTemplate.convertAndSend("/seckill/"+userId,"抱歉,抢购失败");
//        }
        createOrder(userId,seckGoodId);
    }

    /**
     * 创建订单
     * @param userId
     * @param seckGoodId
     */
    public void createOrder(Integer userId, Integer seckGoodId) throws Exception {
        System.out.println(userId+":"+seckGoodId);
        SeckillGood seckillGood=seckillGoodsMapper.findSeckillGood(seckGoodId);
        SeckillOrder seckillOrder = new SeckillOrder();
        User user = new User();
        user.setId(userId);
        seckillOrder.setName(seckillGood.getName());
        seckillOrder.setPrice(seckillGood.getPrice());
        seckillOrder.setId(CommonUtil.generateUUID());
        seckillOrder.setUser(user);
        //生成订单
        System.out.println(1);
        seckillOrderMapper.saveSeckillOrder(seckillOrder);
        //缓存相应数量减少
        jedisTemplate.decr("seckill:stock:id:"+seckGoodId);
        //更新所有缓存秒杀商品
        updateAllSeckillGoods(seckGoodId);
        //数据库减少
        seckillGoodsMapper.updateStockWithOne(seckGoodId);
        //通知用户
        simpMessagingTemplate.convertAndSend("/seckill/"+userId,"恭喜你抢购成功");

        //设置标志位，同一天内不允许同时抢购
        String day = CommonUtil.dateFormat(new Date(), "yyyy-MM-dd");
        //禁止再次抢购
        jedisTemplate.set("seckill:"+day+":"+userId,0,24*60*60);
        System.out.println("抢购成功!");
    }


    @Override
    public boolean seckillGood(Integer seckGoodId, Integer userId) throws Exception {
        Integer flag = jedisTemplate.get("seckill:disabled:id:" + userId, Integer.class);
        if(new Integer(1).equals(flag)){
            return false;
        }else {
            Integer stock = jedisTemplate.get("seckill:stock:id:" + seckGoodId, Integer.class);
            if (stock > 0) {
                Map<String, Object> ids = new HashMap<>();
                ids.put("seckGoodId", seckGoodId);
                ids.put("userId", userId);
                rabbitTemplate.convertAndSend("amq.direct", "queque", ids);
                return true;
            } else {
                return false;
            }
        }
    }
    //更新缓存中所有商品
    private void updateAllSeckillGoods(Integer seckGoodId) throws Exception {
        List<LinkedHashMap<String, Object>> seckillGoods = jedisTemplate.get("seckill:goods", List.class);
        List<LinkedHashMap<String, Object>> result = new ArrayList<LinkedHashMap<String, Object>>();
        for (LinkedHashMap<String, Object> seckillGood : seckillGoods) {
            Integer id = (Integer)seckillGood.get("id");
            if (id.equals(seckGoodId)){
                //存在并修改
                Integer stock = (Integer)seckillGood.get("stock");
                stock=stock-1;
                //修改成功
                seckillGood.put("stock",stock);
            }
            result.add(seckillGood);
        }
        jedisTemplate.set("seckill:goods", result,null);
    }
}
