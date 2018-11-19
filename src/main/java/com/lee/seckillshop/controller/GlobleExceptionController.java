package com.lee.seckillshop.controller;

import com.lee.seckillshop.exception.PayUnkownException;
import com.lee.seckillshop.exception.SeckillGoodIdNotExistException;
import com.lee.seckillshop.exception.SeckillGoodStatusNotExistException;
import com.lee.seckillshop.exception.SeckillUserIdNotExistException;
import com.lee.seckillshop.vo.ResultResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author admin
 * @date 2018-10-19
 */
@RestControllerAdvice
public class GlobleExceptionController {
    @ExceptionHandler
    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    public Object dealException(Exception e){
        if(e instanceof SeckillUserIdNotExistException){
            return new ResultResponse(501,e.getMessage(),null);
        }else if(e instanceof SeckillGoodIdNotExistException){
            return new ResultResponse(502,e.getMessage(),null);
        }else if(e instanceof SeckillGoodStatusNotExistException){
            return new ResultResponse(503,e.getMessage(),null);
        }else if(e instanceof PayUnkownException){
            return new ResultResponse(600,e.getMessage(),null);
        }else{
            return new ResultResponse(504,"网络不稳定哟",null);
        }
    }
}
