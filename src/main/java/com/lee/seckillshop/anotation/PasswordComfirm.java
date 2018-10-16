package com.lee.seckillshop.anotation;

import com.lee.seckillshop.constraint.PasswordComfirmConstraint;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * 自定义的密码验证的注解，用于验证俩次输入的密码是否一致
 */
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = PasswordComfirmConstraint.class)
public @interface PasswordComfirm {
    String message() default "两次密码输入不一次哦";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
