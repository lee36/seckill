package com.lee.seckillshop.exception;

import com.lee.seckillshop.model.SeckillOrder;

/**
 * @author admin
 * 抢购时候的用户名不存在异常类
 * @date 2018-10-19
 */
public class SeckillUserIdNotExistException extends RuntimeException{
    public SeckillUserIdNotExistException(String message){
        super(message);
    }
}
