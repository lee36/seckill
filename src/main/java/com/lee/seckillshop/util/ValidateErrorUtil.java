package com.lee.seckillshop.util;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author admin
 * @date 2018-09-27
 */
public class ValidateErrorUtil {

    public static Map<String, String> buildeError(BindingResult result) {
        Map<String, String> errors = new HashMap<>();
        List<FieldError> fieldErrors = result.getFieldErrors();
        for (FieldError allError : fieldErrors) {
            String param = allError.getField();
            String message = allError.getDefaultMessage();
            errors.put(param, message);
        }
        return errors;
    }
}
