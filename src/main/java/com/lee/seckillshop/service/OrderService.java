package com.lee.seckillshop.service;

import com.lee.seckillshop.commons.exception.PayUnkownException;
import com.lee.seckillshop.commons.model.Order;
import com.lee.seckillshop.commons.model.OrderDetail;
import com.lee.seckillshop.commons.model.SeckillOrder;

import java.util.List;

public interface OrderService {
    public void createSeckillOrder(Integer flag, List<Integer> ids, String orderId, Integer userId, Integer price, List<Integer> nums);

    public void updateState(String out_trade_no) throws PayUnkownException;

    public Object getMyOrder(Integer id,Integer flag);
    //判断是否支付
    Boolean inferOrder(String orderId,int flag);

    List<OrderDetail> getDetailsFromNormal(String id);

    SeckillOrder getDetailsFromSeckill(String id);
}
