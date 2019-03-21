package com.lee.seckillshop.commons.anotation;

import com.lee.seckillshop.commons.constraint.PhoneUniqueConstraint;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * 自定义手机号码的注解，用于验证手机号的唯一性
 */
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = PhoneUniqueConstraint.class)
public @interface PhoneUnique {
    String message() default "手机号已存在";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
