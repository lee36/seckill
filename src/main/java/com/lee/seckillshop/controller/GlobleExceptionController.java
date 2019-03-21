package com.lee.seckillshop.controller;

import com.lee.seckillshop.commons.exception.*;
import com.lee.seckillshop.commons.vo.ResultResponse;
import io.undertow.server.handlers.form.MultiPartParserDefinition;
import org.springframework.http.HttpStatus;
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
    public Object dealException(Exception e) {
        if (e instanceof SeckillUserIdNotExistException) {
            return new ResultResponse(501, e.getMessage(), null);
        } else if (e instanceof SeckillGoodIdNotExistException) {
            return new ResultResponse(502, e.getMessage(), null);
        } else if (e instanceof SeckillGoodStatusNotExistException) {
            return new ResultResponse(503, e.getMessage(), null);
        } else if (e instanceof PayUnkownException) {
            return new ResultResponse(600, e.getMessage(), null);
        } else if(e instanceof MultiPartParserDefinition.FileTooLargeException){
            return new ResultResponse(601, e.getMessage(), null);
        }else if(e instanceof AddSeckillGoodException){
            return new ResultResponse(602, e.getMessage(), null);
        }else if(e instanceof SeckillAllReadyException){
            return new ResultResponse(603, e.getMessage(), null);
        }
        else if(e instanceof SeckillGoodNotFull){
            return new ResultResponse(604, e.getMessage(), null);
        }else {
            return new ResultResponse(504, "网络不稳定哟", null);
        }
    }
}
