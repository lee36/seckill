package com.lee.seckillshop.controller;

import com.lee.seckillshop.commons.model.OrderDetail;
import com.lee.seckillshop.commons.model.ReciverInfo;
import com.lee.seckillshop.commons.model.SeckillOrder;
import com.lee.seckillshop.commons.vo.ResultResponse;
import com.lee.seckillshop.service.OrderService;
import com.lee.seckillshop.service.ReciveInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private ReciveInfoService reciveInfoService;

    @RequestMapping("/getMyOrder")
    public Object getMyOrder(Integer id,Integer flag){
        List orderList = (List)orderService.getMyOrder(id,flag);
        if(orderList!=null&&orderList.size()>0){
          return new ResultResponse(200,"success",orderList);
        }
        return new ResultResponse(500,"error",null);
    }
    @RequestMapping("/infer")
    public Object infer(String orderId,int flag){
        Boolean b=orderService.inferOrder(orderId,flag);
        if(b){
            return new ResultResponse(200,"success",b);
        }
        return new ResultResponse(500,"error",null);
    }
    @GetMapping("/orderDetails")
    public Object getOrderDetails(String id,Integer flag){
        List<OrderDetail> list=null;
         //flag==1从普通订单里面查找
        if(flag==1){
           list=orderService.getDetailsFromNormal(id);
           if(!CollectionUtils.isEmpty(list)) {
               return new ResultResponse(200, "success", list);
           }
           return new ResultResponse(500,"error",null);
        }else{
            SeckillOrder order =orderService.getDetailsFromSeckill(id);
            if(order!=null) {
                return new ResultResponse(200, "success", order);
            }
            return new ResultResponse(500,"error",null);
        }
    }
    @PostMapping("/recive/save")
    public Object saveRecive(@RequestBody ReciverInfo reciverInfo){
        Boolean flag = reciveInfoService.addReciveInfo(reciverInfo);
        if(flag){
           return new ResultResponse(200,"success",flag);
        }
        return new ResultResponse(500,"error",null);
    }
    @GetMapping("/recive/get")
    public Object getRecive(String orderId){
        ReciverInfo reciverInfo=reciveInfoService.getRecive(orderId);
        if(reciverInfo!=null){
            return new ResultResponse(200,"success",reciverInfo);
        }
        return new ResultResponse(500,"error",null);
    }
    @PostMapping("/recive/update")
    public Object updateRecive(@RequestBody ReciverInfo reciverInfo){
        if(reciverInfo.getId()==null){
            Boolean aBoolean = reciveInfoService.addReciveInfo(reciverInfo);
            if(aBoolean){
                return new ResultResponse(200,"success",aBoolean);
            }
            return new ResultResponse(500,"error",null);
        }
        Boolean flag = reciveInfoService.update(reciverInfo);
        if(flag){
            return new ResultResponse(200,"success",flag);
        }
        return new ResultResponse(500,"error",null);
    }
}
