package com.lee.seckillshop.commons.anotation;

import com.lee.seckillshop.commons.constraint.EmailUniqueConstraint;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * 自定义的邮箱注解，用于验证邮箱的唯一性
 */
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = EmailUniqueConstraint.class)
public @interface EmailUnique {
    String message() default "邮箱已存在";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
