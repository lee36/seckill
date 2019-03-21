package com.lee.seckillshop.commons.aop;

import com.lee.seckillshop.commons.exception.AddSeckillGoodException;
import com.lee.seckillshop.commons.model.Goods;
import com.lee.seckillshop.commons.model.SeckillGood;
import com.lee.seckillshop.mapper.GoodsMapper;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class addSeckillAop {
    @Autowired

    private GoodsMapper goodsMapper;

    @Pointcut("execution(* com.lee.seckillshop.service.GoodSeckillService.addSeckillGood(..))")
    public void addSeckill(){}


    @Before("addSeckill()")
    public void before(JoinPoint joinPoint){
        Object[] args = joinPoint.getArgs();
        SeckillGood good=(SeckillGood)args[0];
        Integer goodId = good.getGoodId();
        Goods goods = goodsMapper.findById(goodId);
        if(goods==null){
            throw  new AddSeckillGoodException("没有这个商品");
        }else{
            //不等于空，但是库存不足
            Integer stock = goods.getStock();
            if(stock<good.getStock()){
                throw  new AddSeckillGoodException("库存不足");
            }
        }
    }
}
