package com.lee.seckillshop.form;

import com.sun.tracing.dtrace.ArgsAttributes;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * @author admin
 * @date 2018-09-27
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PasswordValidate {
    private String password;
    private String rePassword;
}
