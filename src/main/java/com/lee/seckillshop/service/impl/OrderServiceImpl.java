package com.lee.seckillshop.service.impl;

import com.lee.seckillshop.commons.exception.PayUnkownException;
import com.lee.seckillshop.mapper.*;
import com.lee.seckillshop.commons.model.*;
import com.lee.seckillshop.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private SeckillOrderMapper seckillOrderMapper;
    @Autowired
    private SeckillGoodsMapper seckillGoodsMapper;
    @Autowired
    private OrderDetailMapper orderDetailMapper;
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private GoodsMapper goodsMapper;

    @Override
    public void createSeckillOrder(Integer flag, List<Integer> ids, String orderId, Integer userId, Integer price, List<Integer> nums) {
        //生成秒杀订单
        if (new Integer(0).equals(flag)) {
            SeckillOrder order = new SeckillOrder();
            order.setId(orderId);
            //根据Id获取商品名称
            Integer id = ids.get(0);
            SeckillGood good = seckillGoodsMapper.findSeckillGood(id);
            order.setName(good.getName());
            order.setPrice(good.getPrice());
            User user = new User();
            user.setId(userId);
            order.setUser(user);
            seckillOrderMapper.saveSeckillOrder(order);
        }
        //生成普通订单
        else {
            //生成订单表
            Order order = new Order();
            order.setId(orderId);
            order.setTotalPrice(price);
            User user = new User();
            user.setId(userId);
            order.setUser(user);
            orderMapper.saveOrder(order);
            //生成订单详情
            for (int i = 0; i < ids.size(); i++) {
                //获取对应的商品
                Goods good = goodsMapper.findById(ids.get(i));
                OrderDetail orderDetail = new OrderDetail();
                orderDetail.setGoodImg(good.getImg());
                orderDetail.setName(good.getName());
                orderDetail.setNum(nums.get(i));
                orderDetail.setPrice(good.getPrice() * nums.get(i));
                orderDetail.setOrder(order);
                orderDetailMapper.saveOrderDetail(orderDetail);
            }
        }
    }

    @Override
    public void updateState(String out_trade_no) throws PayUnkownException {
        Order order = orderMapper.findById(out_trade_no);
        if (null == order) {
            //不存在从秒杀订单中查找
            SeckillOrder seckillOrder = seckillOrderMapper.findById(out_trade_no);
            if (seckillOrder != null) {
                int state = seckillOrderMapper.updateState(out_trade_no, 1);
            } else {
                throw new PayUnkownException("支付异常");
            }

        } else {
            //更新普通订单表
            int count = orderMapper.updateState(1,out_trade_no);
        }

    }

    @Override
    public Object getMyOrder(Integer id,Integer flag) {
        if(flag==1){
            //普通订单
            return orderMapper.getMyOrder(id);
        }else{
            return seckillOrderMapper.getMySeckillOrder(id);
        }

    }

    @Override
    public Boolean inferOrder(String orderId,int flag) {
        if(flag==1){
           //普通订单
            Order order = orderMapper.findById(orderId);
            //支付
            if(order.getStatus()==1){
                return true;
            }
            //未支付
            return false;
        }else{
            //秒杀订单
            SeckillOrder order = seckillOrderMapper.findById(orderId);
            //支付
            if(order.getStatus()==1){
                return true;
            }
            //未支付
            return false;
        }

    }

    @Override
    public List<OrderDetail> getDetailsFromNormal(String id) {
        return orderDetailMapper.findDetailsByOrderId(id);
    }

    @Override
    public SeckillOrder getDetailsFromSeckill(String id) {
        return seckillOrderMapper.findById(id);
    }
}
