package com.lee.seckillshop.service;

import com.lee.seckillshop.commons.exception.PayUnkownException;

import java.util.List;

public interface OrderService {
    public void createSeckillOrder(Integer flag, List<Integer> ids, String orderId, Integer userId, Integer price, List<Integer> nums);

    public void updateState(String out_trade_no) throws PayUnkownException;
}
