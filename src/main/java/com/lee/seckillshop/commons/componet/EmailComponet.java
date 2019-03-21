package com.lee.seckillshop.commons.componet;

import com.lee.seckillshop.commons.util.CommonUtil;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.validation.Valid;

@Component
public class EmailComponet {

    @Autowired
    private JavaMailSender sender;

    @Value("${spring.mail.content}")
    private String content;

    /**
     * 发送给指定email
     * @param email
     * @return
     */
    public String sendEmail(String email) throws MessagingException {
        MimeMessage message=sender.createMimeMessage();
        MimeMessageHelper helper=new MimeMessageHelper(message,true);
        helper.setTo(email);
        helper.setFrom("2950925006@qq.com");
        //设置收信人
        helper.setSubject("修改密码通知");	//设置主题
        String token = CommonUtil.generateUUID().replaceAll("-", "").substring(0, 6);
        helper.setText(String.format(content,token),true);
        sender.send(message);
        return token;
    }
}
