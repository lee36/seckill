package com.lee.seckillshop.commons.constraint;

import com.lee.seckillshop.commons.anotation.PasswordComfirm;
import com.lee.seckillshop.form.PasswordValidate;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author admin
 * @date 2018-09-27
 * 两次密码输入的一样的验证具体实现
 */
@Component
public class PasswordComfirmConstraint implements ConstraintValidator<PasswordComfirm, PasswordValidate> {

    @Override
    public boolean isValid(PasswordValidate passwordValidate, ConstraintValidatorContext constraintValidatorContext) {
        if (passwordValidate == null) {
            return false;
        }
        String password = passwordValidate.getPassword();
        String rePassword = passwordValidate.getRePassword();
        if (password != null && password.equals(rePassword)) {
            return true;
        }
        return false;
    }
}
