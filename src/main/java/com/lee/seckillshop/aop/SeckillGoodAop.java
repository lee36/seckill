package com.lee.seckillshop.aop;


import com.lee.seckillshop.componet.JedisTemplate;
import com.lee.seckillshop.model.SeckillGood;
import jdk.nashorn.internal.scripts.JO;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author admin
 * @date 2018-10-15
 */
@Component
@Aspect
public class SeckillGoodAop {
  @Autowired
  private JedisTemplate jedisTemplate;
  
  @Pointcut("execution(* com.lee.seckillshop.service.impl.GoodSeckillServiceImpl.seckillGood(..))")
  public void seckillGoodAop(){
  }
  @Before("seckillGoodAop()")
  public void beforeSeckil(JoinPoint joinPoint) throws Exception {
      Object[] args = joinPoint.getArgs();
      int goodId=(Integer) args[0];
      List<SeckillGood> list = jedisTemplate.get("seckill:goods", List.class);
      int stock = exist(goodId, list);
      jedisTemplate.set("seckill:stock:"+goodId,stock,null);
  }

    /**
     * 如果存在并且库存大于0返回库存
     * @param goodId
     * @param list
     * @return
     */
  public int exist(int goodId,List<SeckillGood> list){
      if(list==null||list.size()==0){
          return -1;
      }
      for (SeckillGood seckillGood : list) {
          Integer id = seckillGood.getId();
          if(id.equals(goodId)){
              //检查库存
              Integer stock = seckillGood.getStock();
              if(stock>0){
                  return stock;
              }
              return -1;
          }
          return -1;
      }
      return -1;
  }
}
