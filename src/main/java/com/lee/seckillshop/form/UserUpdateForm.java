package com.lee.seckillshop.form;

import com.lee.seckillshop.commons.anotation.EmailUnique;
import com.lee.seckillshop.commons.anotation.PasswordComfirm;
import com.lee.seckillshop.commons.anotation.PhoneUnique;
import com.lee.seckillshop.commons.anotation.UsernameUnique;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * @author admin
 * @date 2018-09-27
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateForm {
    @NotNull
    private Integer id;
    @Pattern(regexp = "^1((3[0-9])|(4[5|7])|(5([0-3]|[5-9]))|(8[0,5-9]))\\d{8}$", message = "请输入正确的手机号")
    @NotNull(message = "手机号不能为空")
    private String phone;
    @NotNull(message = "email不能为空")
    private String email;
    @NotNull
    private Integer status;
    @NotNull
    private Integer identity;
    @NotNull
    private String address;
    @NotNull
    private Integer sex;
    @NotNull
    private String nickname;
}
