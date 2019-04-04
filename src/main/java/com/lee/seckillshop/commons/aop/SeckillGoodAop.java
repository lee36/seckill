package com.lee.seckillshop.commons.aop;


import com.lee.seckillshop.commons.componet.JedisComponet;
import com.lee.seckillshop.commons.exception.SeckillAllReadyException;
import com.lee.seckillshop.commons.exception.SeckillGoodNotFull;
import com.lee.seckillshop.commons.util.CommonUtil;
import com.lee.seckillshop.mapper.SeckillGoodsMapper;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author admin
 * @date 2018-10-15
 */
@Component
@Aspect
public class SeckillGoodAop {
    @Autowired
    private JedisComponet jedisTemplate;
    @Autowired
    private SeckillGoodsMapper seckillGoodsMapper;

    @Pointcut("execution(* com.lee.seckillshop.service.impl.GoodSeckillServiceImpl.seckillGood(..))")
    public void seckillGoodAop() {
    }

    @Before("seckillGoodAop()")
    public void beforeSeckil(JoinPoint joinPoint) throws Exception {
        Object[] args = joinPoint.getArgs();
        int seckillGoodId = (Integer) args[0];
        int userId = (Integer) args[1];
        String day = CommonUtil.dateFormat(new Date(), "yyyy-MM-dd");
        Integer flag = jedisTemplate.get("seckill:" + day + ":" + userId, Integer.class);
        if (new Integer(0).equals(flag)) {
            jedisTemplate.set("seckill:disabled:id:" + userId, 1, 24 * 60 * 60L);
            throw new SeckillAllReadyException("你已经抢购过了，请下次再来!");
        }
        List<LinkedHashMap<String, Object>> seckillGoods = jedisTemplate.get("seckill:goods", List.class);
        if (seckillGoods == null) {
            seckillGoods = seckillGoodsMapper.seckillGoodsList();
            jedisTemplate.set("seckill:goods", seckillGoods, 30L);
        }
        int stock = exist(seckillGoodId, seckillGoods);
        if(stock==-1){
            throw new SeckillGoodNotFull("秒杀商品库存不足");
        }
        //存入当前商品的库存
        jedisTemplate.set("seckill:stock:id:" + seckillGoodId, stock, 30L);
    }

    /**
     * 如果存在并且库存大于0返回库存
     *
     * @param seckillGoodId
     * @param list
     * @return
     */
    private int exist(int seckillGoodId, List<LinkedHashMap<String, Object>> list) {
        //不存在库存
        if (list == null || list.size() == 0) {
            return -1;
        }
        for (LinkedHashMap<String, Object> seckillGood : list) {
            Integer id = (Integer) seckillGood.get("id");
            if (id.equals(seckillGoodId)) {
                //检查库存
                Integer stock = (Integer) seckillGood.get("stock");
                if (stock > 0) {
                    return stock;
                }
                return -1;
            }
        }
        return -1;
    }
}
