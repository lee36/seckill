package com.lee.seckillshop.service.impl;

import com.lee.seckillshop.componet.JedisTemplate;
import com.lee.seckillshop.mapper.SeckillGoodsMapper;
import com.lee.seckillshop.service.GoodSeckillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author admin
 * @date 2018-10-15
 */
@Service
public class GoodSeckillServiceImpl implements GoodSeckillService {
    @Autowired
    private SeckillGoodsMapper seckillGoodsMapper;
    @Autowired
    private JedisTemplate jedisTemplate;
//    public boolean seckillGood(Integer goodId,Integer userId) throws Exception {
//        Integer stock = jedisTemplate.get("seckill:stock:" + goodId, Integer.class);
//        if(stock>0){
//
//        }
//    }
}
