package com.lee.seckillshop.form;

import com.lee.seckillshop.commons.anotation.PasswordComfirm;
import com.lee.seckillshop.commons.anotation.PhoneUnique;
import com.lee.seckillshop.commons.anotation.UsernameUnique;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.*;

/**
 * @author admin
 * @date 2018-09-27
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRegistForm {
    @Pattern(regexp = "^(\\w)\\d{5,14}$", message = "用户名必须以英文开头且6-15位")
    @UsernameUnique(message = "用户名已存在")
    @NotNull(message = "用户名不能为空")
    private String username;
    @NotNull(message = "昵称不能为空")
    private String nickname;
    @Pattern(regexp = "^(?![0-9]+$)[0-9A-Za-z]{6,16}$", message = "密码必须以英文开头6-16位")
    @NotNull(message = "密码不能为空")
    private String password;
    @PasswordComfirm(message = "两次密码输入不一次哦")
    @NotNull(message = "密码不能为空")
    private PasswordValidate ensurePassword;
    private String headImg = "http://localhost:8080/imgs/header.png";
    @NotNull(message = "性别不能为空")
    @Range(message = "不存在该性别", min = 0, max = 1)
    private Integer sex;
    @Pattern(regexp = "^1((3[0-9])|(4[5|7])|(5([0-3]|[5-9]))|(8[0,5-9]))\\d{8}$", message = "请输入正确的手机号")
    @PhoneUnique(message = "手机号已存在")
    @NotNull(message = "手机号不能为空")
    private String phone;
    @NotNull(message = "地区不能为空")
    private String address;
}
