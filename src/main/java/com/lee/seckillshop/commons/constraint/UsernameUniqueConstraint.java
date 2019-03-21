package com.lee.seckillshop.commons.constraint;

import com.lee.seckillshop.commons.anotation.UsernameUnique;
import com.lee.seckillshop.mapper.UserMapper;
import com.lee.seckillshop.commons.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author admin
 * @date 2018-09-26
 */
@Component
public class UsernameUniqueConstraint implements ConstraintValidator<UsernameUnique, String> {
    @Autowired
    private UserMapper userMapper;

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        User user = userMapper.findByUsername(s);
        if (user != null) {
            return false;
        }
        return true;
    }
}
