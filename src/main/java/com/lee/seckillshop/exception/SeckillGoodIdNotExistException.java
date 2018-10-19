package com.lee.seckillshop.exception;

/**
 * @author admin
 * @date 2018-10-19
 */
public class SeckillGoodIdNotExistException extends RuntimeException{
    public SeckillGoodIdNotExistException(String message){
        super(message);
    }
}
