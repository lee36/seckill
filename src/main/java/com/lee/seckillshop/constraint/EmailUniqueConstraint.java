package com.lee.seckillshop.constraint;

import com.lee.seckillshop.anotation.EmailUnique;
import com.lee.seckillshop.mapper.UserMapper;
import com.lee.seckillshop.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author admin
 * @date 2018-09-27
 * 验证邮箱唯一的具体实现
 */
@Component
public class EmailUniqueConstraint implements ConstraintValidator<EmailUnique,String> {
    @Autowired
    private UserMapper userMapper;
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        User user = userMapper.findByEmail(value);
        if(user!=null){
            return false;
        }
        return true;
    }
}
