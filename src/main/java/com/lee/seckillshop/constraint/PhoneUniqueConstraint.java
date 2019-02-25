package com.lee.seckillshop.constraint;

import com.lee.seckillshop.anotation.PhoneUnique;
import com.lee.seckillshop.mapper.UserMapper;
import com.lee.seckillshop.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author admin
 * @date 2018-09-27
 */
@Component
public class PhoneUniqueConstraint implements ConstraintValidator<PhoneUnique, String> {
    @Autowired
    private UserMapper userMapper;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        User user = userMapper.findByPhone(value);
        if (user != null) {
            return false;
        }
        return true;
    }
}
