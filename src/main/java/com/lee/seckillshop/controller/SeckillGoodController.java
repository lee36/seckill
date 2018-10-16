package com.lee.seckillshop.controller;

import com.lee.seckillshop.service.GoodSeckillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author admin
 * @date 2018-10-15
 */
@RestController("/seckill")
public class SeckillGoodController {
    @Autowired
    private GoodSeckillService goodSeckillService;
    @RequestMapping("/{id}")
    public Object seckillGood(@PathVariable("id") int id){
      return null;
    }
}
