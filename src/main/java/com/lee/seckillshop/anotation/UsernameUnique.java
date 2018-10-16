package com.lee.seckillshop.anotation;

import com.lee.seckillshop.constraint.UsernameUniqueConstraint;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.NotNull;
import java.lang.annotation.*;

/**
 * 自定义用户名的注解，用于验证用户名是否唯一
 */
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = UsernameUniqueConstraint.class)
public @interface UsernameUnique {

    String message() default "用户名已存在";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
