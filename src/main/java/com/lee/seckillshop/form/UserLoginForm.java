package com.lee.seckillshop.form;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * @author admin
 * @date 2018-09-26
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginForm {
    @Pattern(regexp = "^(\\w)\\d{5,14}$", message = "用户名格式错误")
    @NotBlank(message = "用户名不能为空")
    private String username;
    @Pattern(regexp = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,16}$", message = "密码格式错误")
    @NotBlank(message = "密码不能为空")
    private String password;
}
