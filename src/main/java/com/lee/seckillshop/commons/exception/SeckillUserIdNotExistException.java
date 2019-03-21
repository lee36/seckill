package com.lee.seckillshop.commons.exception;

/**
 * @author admin
 * 抢购时候的用户名不存在异常类
 * @date 2018-10-19
 */
public class SeckillUserIdNotExistException extends RuntimeException {
    public SeckillUserIdNotExistException(String message) {
        super(message);
    }
}
