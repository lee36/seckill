package com.lee.seckillshop.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lee.seckillshop.commons.model.Collection;
import com.lee.seckillshop.commons.model.Flow;
import com.lee.seckillshop.commons.model.User;
import com.lee.seckillshop.commons.util.ValidateErrorUtil;
import com.lee.seckillshop.commons.vo.ResultResponse;
import com.lee.seckillshop.form.PasswordValidate;
import com.lee.seckillshop.form.UserLoginForm;
import com.lee.seckillshop.form.UserRegistForm;
import com.lee.seckillshop.form.UserUpdateForm;
import com.lee.seckillshop.mapper.UserMapper;
import com.lee.seckillshop.service.FlowService;
import com.lee.seckillshop.service.UserService;
import javafx.util.converter.TimeStringConverter;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/sys")
public class SysUserController {

    @Autowired
    private UserService userService;
    @Autowired
    private FlowService flowService;

    @PostMapping("/login")
    public Object sysLogin(@Valid @RequestBody UserLoginForm loginForm, BindingResult result,HttpSession session){
        if(result.hasErrors()){
            Map<String, String> map = ValidateErrorUtil.buildeError(result);
            return new ResultResponse(503,"用户名密码格式不正确",map);
        }
        User user = userService.sysUserLogin(loginForm);
        if(user!=null){
            if(user.getStatus()==1){
                return new ResultResponse(501,"账号被冻结，请联系管理员",null);
            }
            //保存在session中
            session.setAttribute("user",user);
            session.setAttribute("druid-user","root");
            return new ResultResponse(200,"登录成功",null);
        }
        return new ResultResponse(500,"用户名或者密码错误",null);
    }

    @GetMapping("/current")
    public Object getCurrent(HttpServletRequest request, HttpSession session,HttpServletResponse response){
        User user=(User)session.getAttribute("user");
        if(user!=null){
            return new ResultResponse(200,"success",user);
        }
        return new ResultResponse(500,"error",null);
    }



    @GetMapping("/forget")
    public Object forgetPass(HttpSession session,String email) throws MessagingException {
        //sendEmail
        Boolean b = userService.sendEmail(session,email);
        if(b){
            return new ResultResponse(200,"success",null);
        }
        return new ResultResponse(500,"请输入正确的邮箱",null);
    }

    @GetMapping("/forgetComfirm")
    public Object comfirmToken(HttpSession session,String token){
        Boolean b = userService.checkeEmailToken(session, token);
        if(b){
            return new ResultResponse(200,"success",b);
        }
        return new ResultResponse(500,"error",null);
    }

    @PostMapping("/newPass")
    public Object newPass(@RequestBody PasswordValidate passes,HttpServletRequest request){
        String password = (String)passes.getPassword();
        String rePassword=(String)passes.getRePassword();
        Boolean b = userService.comfireNewPass(request,password,rePassword);
        if(b){
            //修改成功
            return new ResultResponse(200,"success",b);
        }
        return new ResultResponse(500,"error",null);
    }
    @GetMapping("/logout")
    public Object logOut(HttpSession session){
        session.removeAttribute("user");
        session.invalidate();
        return true;
    }

    @GetMapping("/userList")
    public Object userList(@PageableDefault(page = 1,size = 2) Pageable pageable){
        PageHelper.startPage(pageable.getPageNumber(),pageable.getPageSize());
        List<User> userList = userService.userList();
        if(CollectionUtils.isEmpty(userList)){
            return new ResultResponse(500,"没有任何内容",null);
        }
        return new ResultResponse(200,"success",new PageInfo<User>(userList));
    }
    @GetMapping("/delUser")
    public Object delUser(String users){
        String s = users.substring(1, users.length()-1);
        List<Integer> ids = Arrays.asList(s.split(",")).
                stream().map(one->Integer.parseInt(one)).collect(Collectors.toList());
        Boolean flag = userService.delUsers(ids);
        if(flag){
            return new ResultResponse(200,"success",flag);
        }
        return  new ResultResponse(500,"error",null);
    }
    @GetMapping("/getUser")
    public Object updateUser(Integer id){
        User user = userService.findById(id);
        if(user!=null){
            return new ResultResponse(200,"success",user);
        }
        return new ResultResponse(500,"error",null);
    }
    @PostMapping("/updateUser")
    public Object updateUser(@RequestBody @Valid UserUpdateForm form,BindingResult result){
        if(result.hasErrors()){
            return new ResultResponse(500, "error", null);
        }
        try {
            Boolean flag = userService.updateUserInfo(form);
            if (flag) {
                return new ResultResponse(200, "success", flag);
            }
            return new ResultResponse(500, "error", null);
        }catch (Exception e){
            return new ResultResponse(500, "error", null);
        }
    }

    @GetMapping("/cageUser")
    public Object cageUser(String ids,int flag){
        String s = ids.substring(1, ids.length()-1);
        List<Integer> nums = Arrays.asList(s.split(",")).
                stream().map(one->Integer.parseInt(one)).collect(Collectors.toList());
        Boolean b=userService.cageUser(nums,flag);
        if(b){
            return new ResultResponse(200,"success",null);
        }
        return new ResultResponse(500,"error",null);
    }
}
